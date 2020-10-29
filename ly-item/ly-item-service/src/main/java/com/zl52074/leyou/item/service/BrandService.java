package com.zl52074.leyou.item.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.pojo.Brand;

/**
 * @description: 商品品牌service接口
 * @author: zl52074
 * @time: 2020/10/29 14:20
 */
public interface BrandService {
    /**
     * @description 分页查询品牌
     * @param page 页码
     * @param rows 每页显示行数
     * @param sortBy 排序字段
     * @param desc 排序规则
     * @param key 搜索关键字
     * @return com.zl52074.leyou.common.pojo.PageResult<com.zl52074.leyou.item.pojo.Brand>
     * @author zl52074
     * @time 2020/10/29 14:58
     */
    PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key);
}
