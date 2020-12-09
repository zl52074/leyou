package com.zl52074.leyou.item.bo;

import com.zl52074.leyou.item.po.SpecGroup;
import com.zl52074.leyou.item.po.SpecParam;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/3 17:47
 */
@Data
public class SpecGroupBO extends SpecGroup {
    private List<SpecParam> params;
}
