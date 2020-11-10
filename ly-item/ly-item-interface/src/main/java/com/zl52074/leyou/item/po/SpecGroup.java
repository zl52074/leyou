package com.zl52074.leyou.item.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 商品规格组
 * @author: zl52074
 * @time: 2020/11/4 18:58
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long cid;
    private String name;
}
