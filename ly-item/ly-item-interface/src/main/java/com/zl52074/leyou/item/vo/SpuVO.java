package com.zl52074.leyou.item.vo;

import com.zl52074.leyou.item.po.Spu;
import lombok.Data;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/8 14:48
 */
@Data
public class SpuVO extends Spu {
    //分类名称
    private String cname;
    //品牌名称
    private String bname;
}
