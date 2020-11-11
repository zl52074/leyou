package com.zl52074.leyou.item.vo;

import com.zl52074.leyou.item.po.Sku;
import lombok.Data;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/10 16:21
 */
@Data
public class SkuVO extends Sku {
    private Integer stock;
}
