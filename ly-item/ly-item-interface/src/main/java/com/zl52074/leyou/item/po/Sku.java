package com.zl52074.leyou.item.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/10 15:44
 */
@Data
@Table(name = "tb_sku")
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long spuId;//spuId
    private String title;//标题
    private String images;//图片
    private Long price;//价格单位（分）
    private String indexes;//页面位置序号
    private String ownSpec;//商品特殊规格的键值
    private Boolean enable;//是否有效
    private Date createTime;//创建时间
    private Date lastUpdateTime;//最后修改时间

}
