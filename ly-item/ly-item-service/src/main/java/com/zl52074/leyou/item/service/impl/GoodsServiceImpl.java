package com.zl52074.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.vo.SkuVO;
import com.zl52074.leyou.item.mapper.*;
import com.zl52074.leyou.item.po.*;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.vo.SpuVO;
import com.zl52074.leyou.item.service.BrandService;
import com.zl52074.leyou.item.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
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
    private SkuMapper skuMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
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

    /**
     * @description 保存商品信息
     * @param
     * @return void
     * @author zl52074
     * @time 2020/11/10 16:42
     */
    @Transactional
    @Override
    public void saveGoods(SpuBO spuBO) {
        //保存Spu
        Spu spu = new Spu();
        spu.setId(null); //手动id置null
        BeanUtils.copyProperties(spuBO, spu);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //保存spuDetail
        SpuDetail spuDetail = spuBO.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(spuDetail);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        saveSkuAndStock(spuBO, spu);
    }

    /**
     * @description 新增商品sku和stock
     * @param spuBO
     * @return void
     * @author zl52074
     * @time 2020/11/11 15:31
     */
    private void saveSkuAndStock(SpuBO spuBO,Spu spu){
        //保存Sku
        List<Sku> skus = new ArrayList<>();
        List<SkuStock> stocks = new ArrayList<>();
        List<SkuVO> skuVOS = spuBO.getSkus();
        for(SkuVO skuVO : skuVOS){
            Sku sku = new Sku();
            SkuStock stock = new SkuStock();
            BeanUtils.copyProperties(skuVO, sku);
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            int count = skuMapper.insert(sku);
            if(count!=1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            BeanUtils.copyProperties(skuVO, stock);
            stock.setSkuId(sku.getId());
            stocks.add(stock);
        }
        //保存SkuStock
        int count = skuStockMapper.insertList(stocks);
        if(count!=stocks.size()){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    /**
     * @description 根据spuId删除sku和skuStock
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/11/11 17:45
     */
    private void deleteSkuAndStock(Long spuId){
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if(!CollectionUtils.isEmpty(skus)){
            skuMapper.delete(sku); //删除sku
            List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
            skuStockMapper.deleteByIdList(ids);//删除stock
        }
    }

    /**
     * @description 根据spuId查询spuDetail
     * @param  spuId
     * @return com.zl52074.leyou.item.po.SpuDetail
     * @author zl52074
     * @time 2020/11/11 8:09
     */
    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if(spuDetail==null){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }

    /**
     * @description 根据spuId查询spu下的sku列表
     * @param spuId
     * @return java.util.List<com.zl52074.leyou.item.vo.SkuVO>
     * @author zl52074
     * @time 2020/11/11 8:33
     */
    @Override
    public List<SkuVO> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        List<SkuVO> skuVOS = new ArrayList<>();
        for(Sku s:skus){
            SkuVO skuVO = new SkuVO();
            BeanUtils.copyProperties(s, skuVO);
//            SkuStock stock = skuStockMapper.selectByPrimaryKey(s.getId());
//            skuVO.setStock(stock.getStock());
            skuVOS.add(skuVO);
        }

        //获取集合的id集合
        List<Long> ids = skuVOS.stream().map(SkuVO::getId).collect(Collectors.toList());
        //根据id集合查出对应的库存集合
        List<SkuStock> stocks = skuStockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stocks)){
            throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
        }
        //库存集合转map  键skuId 值stock
        Map<Long,Integer> stockMap = stocks.stream().collect(Collectors.toMap(SkuStock::getSkuId,SkuStock::getStock));
        //遍历skuVO列表 根据id从map中取值并setStock
        skuVOS.forEach(skuVO -> skuVO.setStock(stockMap.get(skuVO.getId())));
        return skuVOS;
    }


    /**
     * @description 更新商品
     * @param spuBO
     * @return void
     * @author zl52074 
     * @time 2020/11/11 14:39
     */
    @Transactional
    @Override
    public void updateGoods(SpuBO spuBO) {
        //删除sku和skuStock
        deleteSkuAndStock(spuBO.getId());
        //转换spu
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuBO, spu);
        //修改spu
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setSaleable(null);
        spu.setValid(null);
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        //修改spuDetail
        SpuDetail spuDetail = spuBO.getSpuDetail();
        count = spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        //重新添加sku和skuStock
        saveSkuAndStock(spuBO, spu);
    }


    /**
     * @description 更新商品状态
     * @param s
     * @return void
     * @author zl52074
     * @time 2020/11/11 17:35
     */
    @Override
    public void updateSpuStatus(Spu s) {
        //更新上下架状态
        Spu spu = new Spu();
        spu.setId(s.getId());
        spu.setSaleable(s.getSaleable());
        spu.setLastUpdateTime(new Date());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_SPU_UPDATE_ERROR);
        }
    }

    /**
     * @description 根据spuId删除商品（此处默认为物理删除）
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/11/11 17:35
     */
    @Transactional
    @Override
    public void deleteGoodsBySpuId(Long spuId) {
        //删除sku和skuStock
        deleteSkuAndStock(spuId);
        //删除spuDetail
        int count = spuDetailMapper.deleteByPrimaryKey(spuId);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_DELETE_ERROR);
        }
        //删除spu
        count = spuMapper.deleteByPrimaryKey(spuId);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_DELETE_ERROR);
        }
    }

}
