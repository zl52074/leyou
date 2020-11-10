package com.zl52074.leyou.item.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @description: 商品品牌
 * @author: zl52074
 * @time: 2020/10/29 14:08
 */
@Data
@Table(name = "tb_brand")
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;
    private List<Category> categories;
}
