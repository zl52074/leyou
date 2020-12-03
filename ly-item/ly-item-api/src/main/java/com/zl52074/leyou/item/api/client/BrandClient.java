package com.zl52074.leyou.item.api.client;

import com.zl52074.leyou.item.po.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("item-service")
@RequestMapping("brand")
public interface BrandClient {

    /**
     * @description 根据品牌id查询品牌
     * @param id
     * @return org.springframework.http.ResponseEntity<com.zl52074.leyou.item.po.Brand>
     * @author zl52074
     * @time 2020/11/13 17:31
     */
    @GetMapping("id/{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);

    /**
     * @description 根据id集合查找brand
     * @param ids
     * @return java.util.List<com.zl52074.leyou.item.po.Brand>
     * @author zl52074
     * @time 2020/12/1 15:18
     */
    @PostMapping("list/ids")
    public List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
