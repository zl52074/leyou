package com.zl52074.leyou.item.mapper;

import com.zl52074.leyou.item.po.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description: 商品品牌mapper
 * @author: zl52074
 * @time: 2020/10/29 14:21
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    /**
     * @description 插入品牌分类关系中间表
     * @param cid
     * @param bid
     * @return int
     * @author zl52074
     * @time 2020/11/2 22:41
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    public int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    /**
     * @description 根据品牌id删除品牌分类关系
     * @param bid
     * @return void
     * @author zl52074
     * @time 2020/11/2 22:40
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    public void deleteCategoryBrandByBid(@Param("bid") Long bid);

    /**
     * @description 根据分类id查询品牌
     * @param cid
     * @return java.util.List<com.zl52074.leyou.item.pojo.Brand>
     * @author zl52074
     * @time 2020/11/10 14:33
     */
    @Select("SELECT * FROM tb_brand b JOIN tb_category_brand cb ON " +
            "b.id = cb.brand_id " +
            "WHERE cb.category_id = #{cid}")
    public List<Brand> selectBrandByCid(@Param("cid") Long cid);
}
