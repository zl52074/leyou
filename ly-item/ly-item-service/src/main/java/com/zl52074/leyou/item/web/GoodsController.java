package com.zl52074.leyou.item.web;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.po.Sku;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.item.po.SpuDetail;
import com.zl52074.leyou.item.vo.SkuVO;
import com.zl52074.leyou.item.vo.SpuVO;
import com.zl52074.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PageResult<SpuVO>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }

    /**
     * @description 分页仅仅查询spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.vo.SpuVO>>
     * @author zl52074
     * @time 2020/11/10 17:37
     */
    @GetMapping("spu/only/page")
    public ResponseEntity<PageResult<Spu>> querySpuOnlyByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuOnlyByPage(page,rows,saleable,key));
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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @description 更新商品
     * @param spuBO
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/11 14:27
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBO spuBO){
        goodsService.updateGoods(spuBO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    /**
     * @description 根据spuId查询spuDetail
     * @param spuId
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.item.po.SpuDetail>
     * @author zl52074
     * @time 2020/11/11 8:08
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return ResponseEntity.ok(goodsService.querySpuDetailBySpuId(spuId));
    }

    /**
     * @description 根据spuId查询spu下的sku列表
     * @param
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.vo.SpuVO>>
     * @author zl52074
     * @time 2020/11/11 8:29
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<SkuVO>> querySkuBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }

    /**
     * @description 更改商品状态
     * @param
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/11 17:20
     */
    @PutMapping("spu")
    public ResponseEntity<Void> updateSpuStatus(@RequestBody()Spu spu){
        goodsService.updateSpuStatus(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @description 根据spuId删除商品
     * @param spuId
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/11/11 17:28
     */
    @DeleteMapping("goods")
    public ResponseEntity<Void> deleteGoods(@RequestParam("spuId") Long spuId){
        goodsService.deleteGoodsBySpuId(spuId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("sku/only/List")
    public ResponseEntity<List<Sku>> querySkuBySpuIdWithoutStock(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySkuOnlyBySpuId(spuId));
    }

}
