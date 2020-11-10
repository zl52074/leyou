package com.zl52074.leyou.item.service;

import com.zl52074.leyou.item.po.Category;

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

    /**
     * @description 根据品牌id查询对应的分类列表
     * @param bid
     * @return java.util.List<com.zl52074.leyou.item.pojo.Category>
     * @author zl52074
     * @time 2020/11/2 22:28
     */
    public List<Category> queryCategoryByBid(Long bid);


    /**
     * @description 根据id集合查分类
     * @param ids
     * @return java.util.List<com.zl52074.leyou.item.pojo.Category>
     * @author zl52074
     * @time 2020/11/9 20:22
     */
    public List<Category> queryCategoryById(List<Long> ids);
}
