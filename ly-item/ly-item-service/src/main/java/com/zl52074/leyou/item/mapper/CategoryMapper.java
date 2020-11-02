package com.zl52074.leyou.item.mapper;

import com.zl52074.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description: 商品分类mapper
 * @author: zl52074
 * @time: 2020/10/27 21:22
 */
@Repository
public interface CategoryMapper extends Mapper<Category> {

    /**
     * @description 根据品牌id查询分类id
     * @param bid
     * @return java.util.List<java.lang.Long>
     * @author zl52074
     * @time 2020/11/2 22:29
     */
    @Select("select category_id from tb_category_brand where brand_id = #{bid}")
    public List<Long> selectCategoryIdByBid(@Param("bid") Long bid);

}
