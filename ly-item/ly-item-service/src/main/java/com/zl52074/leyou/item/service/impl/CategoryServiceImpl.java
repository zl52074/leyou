package com.zl52074.leyou.item.service.impl;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.item.mapper.CategoryMapper;
import com.zl52074.leyou.item.pojo.Category;
import com.zl52074.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @description: 商品分类service实现
 * @author: zl52074
 * @time: 2020/10/27 21:26
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @description 根据pid查询category列表
     * @param pid
     * @return java.util.List<com.zl52074.leyou.item.pojo.Category>
     * @author zl52074
     * @time 2020/10/27 21:31
     */
    @Override
    public List<Category> queryCategoryListByPid(Long pid) {
        //对象封装查询条件
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categories = categoryMapper.select(category);
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categories;
    }
}
