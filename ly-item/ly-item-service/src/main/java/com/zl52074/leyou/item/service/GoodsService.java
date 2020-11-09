package com.zl52074.leyou.item.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.pojo.vo.SpuVO;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:26
 */
public interface GoodsService {
    PageResult<SpuVO> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);
}
