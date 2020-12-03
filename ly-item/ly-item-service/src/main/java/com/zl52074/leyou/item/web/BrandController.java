package com.zl52074.leyou.item.web;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.po.Brand;
import com.zl52074.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * @description 品牌新增
     * @param brand 品牌实体类
     * @param cids 品牌对应分类id
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/10/30 10:07
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @description 更新品牌
     * @param brand
     * @param cids
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/13 17:26
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.updateBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @description 删除品牌
     * @param bid
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/13 17:26
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestParam("bid") Long bid){
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @description 根据分类id查询品牌
     * @param cid
     * @return java.util.List<com.zl52074.leyou.item.pojo.Brand>
     * @author zl52074
     * @time 2020/11/10 14:10
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * @description 根据品牌id查询品牌
     * @param id
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.item.po.Brand>
     * @author zl52074
     * @time 2020/11/13 17:31
     */
    @GetMapping("id/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.queryBrandById(id));
    }

    /**
     * @description 根据id集合查找brand
     * @param ids
     * @return java.util.List<com.zl52074.leyou.item.po.Brand>
     * @author zl52074
     * @time 2020/12/1 15:18
     */
    @PostMapping("list/ids")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.queryBrandByIds(ids));
    }
}
