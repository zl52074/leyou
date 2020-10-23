package com.zl52074.leyou.item.web;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.item.pojo.Item;
import com.zl52074.leyou.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/20 23:10
 */
@RestController
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //测试1
    @PostMapping
    public ResponseEntity<Item> saveItem(Item item) {
        if(item.getPrice()==null){
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        System.out.println(111);
        item = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
