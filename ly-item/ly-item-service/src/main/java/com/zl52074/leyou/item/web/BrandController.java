package com.zl52074.leyou.item.web;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.pojo.Brand;
import com.zl52074.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 商品品牌Controller
 * @author: zl52074
 * @time: 2020/10/29 14:15
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * @description 分页查询品牌
     * @param page 页码
     * @param rows 每页显示行数
     * @param sortBy 排序字段
     * @param desc 排序规则
     * @param key 搜索关键字
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.pojo.Brand>>
     * @author zl52074 
     * @time 2020/10/29 14:53
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,key));
    }
}
