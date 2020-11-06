package com.zl52074.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/6 10:43
 */
@Data
@Table(name="tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long brandId;
    private Long cid1;//一级分类
    private Long cid2;//二级分类
    private Long cid3;//三级分类
    private String title;//标题
    private String subTitle;//子标题
    private Boolean saleable;//是否上架
    private Boolean valid;//是否有效，逻辑删除用
    private Date createTime;//创建时间
    private Date lastUpdateTime;//最后修改时间
}
