package com.zl52074.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.mapper.BrandMapper;
import com.zl52074.leyou.item.pojo.Brand;
import com.zl52074.leyou.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Base64;
import java.util.List;

/**
 * @description: 商品品牌service实现类
 * @author: zl52074
 * @time: 2020/10/29 14:20
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * @description
     * @description 分页查询品牌
     * @param page 页码
     * @param rows 每页显示行数
     * @param sortBy 排序字段
     * @param desc 排序规则
     * @param key 搜索关键字
     * @return com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.pojo.Brand>
     * @author zl52074
     * @time 2020/10/29 14:59
     */
    @Override
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        //分页
        if(rows!=-1){
            PageHelper.startPage(page,rows);
        }
        //过滤
        //example: name like "%x%" or letter = "x"
        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("name", "%"+key+"%")
                    .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            //example: id DESC
            String orderByClause = sortBy+(desc?" DESC":" ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo info = new PageInfo(brands);
        return new PageResult<Brand>(info.getTotal(),brands);
    }

    /**
     * @description 品牌新增
     * @param brand 品牌实体类
     * @param cids 品牌对应分类id
     * @return void
     * @author zl52074
     * @time 2020/10/30 10:09
     */
    @Transactional
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        for(Long cid:cids){
            count = brandMapper.insertCategoryBrand(cid,brand.getId());
            if(count != 1){
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }

    }
}
