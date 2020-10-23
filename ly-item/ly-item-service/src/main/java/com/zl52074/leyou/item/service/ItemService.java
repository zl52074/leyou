package com.zl52074.leyou.item.service;

import com.zl52074.leyou.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/20 23:07
 */
@Service
public class ItemService {

    public Item saveItem(Item item){
        int id = new Random().nextInt(100);
        item.setId(id);
        return item;
    }












}
