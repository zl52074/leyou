package com.zl52074.leyou.item.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.vo.SpuVO;

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
     * @description 保存商品信息
     * @param
     * @return void
     * @author zl52074
     * @time 2020/11/10 16:40
     */
    public void saveGoods(SpuBO spuBO);
}
