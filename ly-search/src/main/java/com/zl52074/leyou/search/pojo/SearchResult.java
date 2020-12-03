package com.zl52074.leyou.search.pojo;

import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.po.Brand;
import com.zl52074.leyou.item.po.Category;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/1 10:46
 */
@Data
public class SearchResult extends PageResult<Goods> {
    private List<Category> categories;
    private List<Brand> brands;
    private List<Map<String,Object>> specs;

    public SearchResult(){
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String,Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
