package com.zl52074.leyou.item.service;

import com.zl52074.leyou.item.pojo.Category;

import java.util.List;

/**
 * @description: 商品分类service接口
 * @author: zl52074
 * @time: 2020/10/27 21:24
 */
public interface CategoryService {

    /**
     * @description 根据pid查询category列表
     * @param pid
     * @return java.util.List<com.zl52074.leyou.item.pojo.Category>
     * @author zl52074
     * @time 2020/10/27 21:31
     */
    public List<Category> queryCategoryListByPid(Long pid);
}
