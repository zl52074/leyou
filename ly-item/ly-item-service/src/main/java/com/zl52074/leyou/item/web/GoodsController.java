package com.zl52074.leyou.item.web;

import com.zl52074.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:36
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


}
