package com.zl52074.leyou.item.api.client;

import com.zl52074.leyou.item.po.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/15 22:19
 */
@FeignClient("item-service")
@RequestMapping("category")
public interface CategoryClient {
    /**
     * @description 根据id查找分类集合
     * @param ids
     * @return org.springframework.http.ResponseEntity<java.util.List<com.zl52074.leyou.item.po.Category>>
     * @author zl52074
     * @time 2020/11/13 17:24
     */
    @GetMapping("list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
