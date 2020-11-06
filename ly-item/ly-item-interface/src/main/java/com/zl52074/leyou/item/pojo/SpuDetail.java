package com.zl52074.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/6 10:52
 */
@Data
@Table(name="tb_spu_detail")
public class SpuDetail {
    @Id
    private Long spuId;
    private String description;//商品描述
    private String specialSpec;//商品特殊规格的名称以及可选值模版
    private String genericSpec;//商品的全局规格属性
    private String packingList;//包装清单
    private String afterService;//售后服务

}
