package com.zl52074.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.mapper.CategoryMapper;
import com.zl52074.leyou.item.mapper.SpuDetailMapper;
import com.zl52074.leyou.item.mapper.SpuMapper;
import com.zl52074.leyou.item.pojo.Brand;
import com.zl52074.leyou.item.pojo.Category;
import com.zl52074.leyou.item.pojo.Spu;
import com.zl52074.leyou.item.pojo.vo.SpuVO;
import com.zl52074.leyou.item.service.BrandService;
import com.zl52074.leyou.item.service.CategoryService;
import com.zl52074.leyou.item.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 商品管理controller
 * @author: zl52074
 * @time: 2020/11/8 14:27
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandService brandService;

    @Override
    public PageResult<SpuVO> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        if(rows!=-1){
            PageHelper.startPage(page,rows);
        }
        //过滤 这里用 andLike
        Example example = new Example(Spu.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().andLike("title", "%"+key+"%");
        }
        if(saleable!=null){
            example.createCriteria().andEqualTo("saleable",saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time DESC");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //Spu转SpuVO
        List<SpuVO> spuVOs = new ArrayList<SpuVO>();
        for (Spu spu : spus) {
            SpuVO spuVO = new SpuVO();
            BeanUtils.copyProperties(spu,spuVO);
            spuVO.setCname(getCategoryName(spu));
            spuVO.setBname(brandService.queryBrandById(spu.getBrandId()).getName());
            spuVOs.add(spuVO);
        }
        //解析分页结果
        PageInfo info = new PageInfo(spus);
        return new PageResult<SpuVO>(info.getTotal(),spuVOs);
    }

    /**
     * @description 将spu中的分类id转换成中文名称
     * @param spu
     * @return java.lang.String
     * @author zl52074
     * @time 2020/11/9 20:48
     */
    private String getCategoryName(Spu spu){
        //处理分类名称
        List<String> names = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()))
                .stream().map(Category::getName).collect(Collectors.toList());
        return StringUtils.join(names,"/");
    }

}
