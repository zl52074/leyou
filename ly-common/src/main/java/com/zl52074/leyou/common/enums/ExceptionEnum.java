package com.zl52074.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/21 0:23
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public  enum ExceptionEnum {
    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(404,"商品分类未查到");
    private int code;
    private String msg;
}
