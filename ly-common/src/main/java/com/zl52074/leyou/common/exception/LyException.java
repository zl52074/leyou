package com.zl52074.leyou.common.exception;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/21 0:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
