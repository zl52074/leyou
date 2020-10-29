package com.zl52074.leyou.item.web;

import com.zl52074.leyou.item.pojo.Category;
import com.zl52074.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
