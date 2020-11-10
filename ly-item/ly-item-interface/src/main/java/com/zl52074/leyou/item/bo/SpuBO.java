package com.zl52074.leyou.item.bo;

import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.item.po.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/10 14:02
 */
@Data
public class SpuBO extends Spu {
    private SpuDetail spuDetail;
    private List<SkuBO> skus;
}
