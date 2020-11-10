package com.zl52074.leyou.item.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/10 16:04
 */
@Data
@Table(name = "tb_stock")
public class SkuStock {
    @Id
    private Long skuId;
    private Integer seckillStock;
    private Integer seckillTotal;
    private Integer stock;
}
