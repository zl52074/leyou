package com.zl52074.leyou.item.web;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.vo.SpuVO;
import com.zl52074.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:36
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * @description 分页查询spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.vo.SpuVO>>
     * @author zl52074
     * @time 2020/11/10 17:37
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuVO>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }

    /**
     * @description 保存商品
     * @param spuBO
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/10 17:37
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBO spuBO){
        goodsService.saveGoods(spuBO);
        return ResponseEntity.ok().build();
    }




}
