package com.zl52074.leyou.user.service;

import com.zl52074.leyou.user.po.UserPO;
import com.zl52074.leyou.user.vo.UserVO;

public interface UserService {
    /**
     * @description 校验数据是否可用
     * @param data 数据
     * @param type 类型（用户名，手机）
     * @return java.lang.Boolean
     * @author zl52074
     * @time 2020/12/18 10:25
     */
    Boolean checkData(String data, Integer type);

    /**
     * @description 发送短信验证码
     * @param phone 手机号
     * @return void
     * @author zl52074
     * @time 2020/12/18 17:01
     */
    void sendSmsCode(String phone);

    /**
     * @description 注册
     * @param user
     * @param code
     * @return void
     * @author zl52074
     * @time 2020/12/22 15:43
     */
    void register(UserPO user, String code);

    /**
     * @description 根据用户名和密码返回用户
     * @param username
     * @param password 
     * @return com.zl52074.leyou.user.pojo.User
     * @author zl52074 
     * @time 2020/12/22 18:49
     */
    UserVO queryUserByUsernameAndPassword(String username, String password);
}
