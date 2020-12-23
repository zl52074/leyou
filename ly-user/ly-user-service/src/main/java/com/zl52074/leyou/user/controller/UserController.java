package com.zl52074.leyou.user.controller;

import com.zl52074.leyou.user.po.UserPO;
import com.zl52074.leyou.user.service.UserService;
import com.zl52074.leyou.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/18 10:15
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @description 校验用户名，手机号是否可用
     * @param data
     * @param type
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
     * @author zl52074
     * @time 2020/12/22 15:45
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data,@PathVariable("type") Integer type){
        return ResponseEntity.ok(userService.checkData(data,type));
    }

    /**
     * @description 发送验证码
     * @param phone
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/12/22 15:46
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendSmsCode(String phone){
        userService.sendSmsCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @description 注册
     * @param user
     * @param code
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author zl52074
     * @time 2020/12/22 15:46
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid UserPO user, BindingResult result, @RequestParam("code") String code){
        if(result.hasFieldErrors()){
            throw new RuntimeException(result.getFieldErrors().stream()
                    .map(e->e.getDefaultMessage()).collect(Collectors.joining("|")));
        }
        userService.register(user,code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("query")
    public ResponseEntity<UserVO> queryUserByUsernameAndPassword(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username,password));
    }

}
