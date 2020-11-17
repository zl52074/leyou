package com.zl52074.leyou.item.api.client;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.po.Sku;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.item.po.SpuDetail;
import com.zl52074.leyou.item.vo.SpuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface GoodsClient {
    /**
     * @description 根据spuId查询spu下的sku列表
     * @param
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.vo.SpuVO>>
     * @author zl52074
     * @time 2020/11/11 8:29
     */
    @GetMapping("sku/only/List")
    public List<Sku> querySkuOnlyBySpuId(@RequestParam("id") Long spuId);

    /**
     * @description 根据spuId查询spuDetail
     * @param spuId
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.item.po.SpuDetail>
     * @author zl52074
     * @time 2020/11/11 8:08
     */
    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

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
    public PageResult<Spu> querySpuOnlyByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    );
}
