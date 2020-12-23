package com.zl52074.leyou.user.service.impl;

import com.zl52074.leyou.common.enums.ExceptionEnum;
import com.zl52074.leyou.common.exception.LyException;
import com.zl52074.leyou.common.utils.NumberUtils;
import com.zl52074.leyou.user.mapper.UserMapper;
import com.zl52074.leyou.user.po.UserPO;
import com.zl52074.leyou.user.service.UserService;
import com.zl52074.leyou.user.utils.CodecUtils;
import com.zl52074.leyou.user.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/18 10:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    /**
     * @description 校验数据是否可用
     * @param data 数据
     * @param type 类型（用户名，手机）
     * @return java.lang.Boolean
     * @author zl52074
     * @time 2020/12/18 10:25
     */
    @Override
    public Boolean checkData(String data, Integer type) {
        UserPO record = new UserPO();
        switch (type){
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            case 3:
                throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        return userMapper.selectCount(record)==0;
    }

    /**
     * @description 发送验证码
     * @param phone
     * @return void
     * @author zl52074
     * @time 2020/12/22 15:44
     */
    @Override
    public void sendSmsCode(String phone) {
        String pattern = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";
        if(!Pattern.matches(pattern,phone)){
            throw new LyException(ExceptionEnum.INVALID_PHONE_NUMBER);
        }
        String key = KEY_PREFIX+phone;
        //生成随机验证码
        String code = NumberUtils.generateCode(6);
        Map<String,String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        //发送验证码
        amqpTemplate.convertAndSend( "sms.verify.code",msg);
        //写入Redis
        redisTemplate.opsForValue().set(key, code,5L,TimeUnit.MINUTES);
    }

    /**
     * @description 注册
     * @param user
     * @param code
     * @return void
     * @author zl52074
     * @time 2020/12/22 15:44
     */
    @Override
    public void register(UserPO user, String code) {
        //校验验证码
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX+user.getPhone());
        if(!StringUtils.equals(code, cacheCode)){
            throw new LyException(ExceptionEnum.INVALID_VERIFY_CODE);
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setCreated(new Date());
        //存储用户
        userMapper.insert(user);
    }

    @Override
    public UserVO queryUserByUsernameAndPassword(String username, String password) {
        //根据用户名查询用户
        UserPO record = new UserPO();
        record.setUsername(username);
        UserPO user =  userMapper.selectOne(record);
        //判断用户是否存在
        if(user == null){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
        }
        System.out.println(CodecUtils.md5Hex(password, user.getSalt()));
        //判断密码是否正确
        if(!StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()))){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
        }
        //转换实体类返回
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;

    }
}
