package com.zl52074.leyou.item.service.impl;

import com.zl52074.leyou.item.mapper.SpuDetailMapper;
import com.zl52074.leyou.item.mapper.SpuMapper;
import com.zl52074.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:27
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
}
