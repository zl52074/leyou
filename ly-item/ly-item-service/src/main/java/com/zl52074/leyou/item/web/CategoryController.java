package com.zl52074.leyou.item.web;

import com.zl52074.leyou.item.po.Category;
import com.zl52074.leyou.item.service.CategoryService;
import com.zl52074.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 商品分类Controller
 * @author: zl52074
 * @time: 2020/10/27 21:28
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * @description 根据父节点查询商品分类
     * @param pid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.pojo.Category>>
     * @author zl52074
     * @time 2020/10/27 21:37
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid){
        //OK 状态码：200
        ResponseEntity.status(HttpStatus.OK).body(categoryService.queryCategoryListByPid(pid));
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    /**
     * @description 根据brandId查询关联的分类列表
     * @param bid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.pojo.Category>>
     * @author zl52074
     * @time 2020/11/2 21:55
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryBybid(@PathVariable("bid") Long bid){
        return ResponseEntity.ok(categoryService.queryCategoryByBid(bid));
    }



}
