package com.zl52074.leyou.user.vo;

import lombok.Data;
import java.util.Date;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/17 19:03
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String phone;
    private Date created;
}
