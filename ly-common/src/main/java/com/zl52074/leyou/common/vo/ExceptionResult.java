package com.zl52074.leyou.common.vo;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/21 0:36
 */
@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
