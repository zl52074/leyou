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
    CATEGORY_NOT_FOUND(404,"商品分类未查到"),
    BRAND_NOT_FOUND(404,"商品品牌未查到"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    UPLOAD_FILE_ERROR(500,"文件上传失败"),
    BRAND_UPDATE_ERROR(500,"更新品牌失败"),
    BRAND_DELETE_ERROR(500,"删除品牌失败"),
    SPEC_GROUPS_NOT_FOUND(404,"商品规格组未查到"),
    SPEC_GROUP_UPDATE_ERROR(500,"规格组更新失败"),
    SPEC_GROUP_SAVE_ERROR(500,"规格组保存失败"),
    SPEC_GROUP_DELETE_ERROR(500,"规格组删除失败"),
    SPEC_PARAMS_NOT_FOUND(404,"商品规格参数未查到"),
    SPEC_PARAM_SAVE_ERROR(500,"规格参数保存失败"),
    SPEC_PARAM_UPDATE_ERROR(500,"规格参数更新失败"),
    SPEC_PARAM_DELETE_ERROR(500,"规格参数删除失败")
    ;
    private int code;
    private String msg;
}
