package com.zl52074.leyou.item.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.po.Sku;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.item.po.SpuDetail;
import com.zl52074.leyou.item.vo.SkuVO;
import com.zl52074.leyou.item.vo.SpuVO;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:26
 */
public interface GoodsService {

    /**
     * @description 分页查询Spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.pojo.vo.SpuVO>
     * @author zl52074
     * @time 2020/11/10 16:40
     */
    public PageResult<SpuVO> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * @description 分页仅查询Spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.pojo.vo.SpuVO>
     * @author zl52074
     * @time 2020/11/10 16:40
     */
    public PageResult<Spu> querySpuOnlyByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * @description 保存商品信息
     * @param
     * @return void
     * @author zl52074
     * @time 2020/11/10 16:40
     */
    public void saveGoods(SpuBO spuBO);

    /**
     * @description 根据spuId查询spuDetail
     * @param  spuId
     * @return com.zl52074.leyou.item.po.SpuDetail
     * @author zl52074
     * @time 2020/11/11 8:09
     */
    public SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * @description 根据spuId查询spu下的sku列表
     * @param spuId
     * @return java.util.List<com.zl52074.leyou.item.vo.SkuVO>
     * @author zl52074
     * @time 2020/11/11 8:33
     */
    List<SkuVO> querySkuBySpuId(Long spuId);

    /**
     * @description 查询sku不包含库存
     * @param spuId
     * @return java.util.List<com.zl52074.leyou.item.po.Sku>
     * @author zl52074
     * @time 2020/11/17 15:51
     */
    List<Sku> querySkuOnlyBySpuId(Long spuId);

    /**
     * @description 更新商品
     * @param spuBO
     * @return void
     * @author zl52074
     * @time 2020/11/11 14:38
     */
    public void updateGoods(SpuBO spuBO);

    /**
     * @description 更新商品状态
     * @param spu
     * @return void
     * @author zl52074
     * @time 2020/11/11 17:35
     */
    public void updateSpuStatus(Spu spu);

    /**
     * @description 根据spuId删除商品
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/11/11 17:35
     */
    public void deleteGoodsBySpuId(Long spuId);


}
