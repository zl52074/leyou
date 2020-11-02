package com.zl52074.leyou.item.service;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.pojo.Brand;

import java.util.List;

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

    /**
     * @description 品牌新增
     * @param brand 品牌实体类
     * @param cids 品牌对应分类id
     * @return void
     * @author zl52074
     * @time 2020/10/30 10:09
     */
    public void saveBrand(Brand brand, List<Long> cids);

    /**
     * @description 更新品牌信息
     * @param brand
     * @param cids
     * @return void
     * @author zl52074
     * @time 2020/11/2 22:45
     */
    public void updateBrand(Brand brand, List<Long> cids);

    /**
     * @description 根据id删除品牌
     * @param bid
     * @return void
     * @author zl52074
     * @time 2020/11/2 23:06
     */
    public void deleteBrand(Long bid);
}
