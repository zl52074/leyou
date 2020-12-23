package com.zl52074.leyou.user.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/17 19:03
 */
@Table(name="tb_user")
@Data
public class UserPO {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @Length(min = 4,max = 32,message = "密码长度必须为4-32位")
    private String password;
    @Pattern(regexp = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$",message = "无效的手机号")
    private String phone;
    private Date created;
    private String salt;
}
