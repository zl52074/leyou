package com.zl52074.leyou.item.mapper;

import com.zl52074.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description: 商品品牌mapper
 * @author: zl52074
 * @time: 2020/10/29 14:21
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    public int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);
}
