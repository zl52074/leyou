package com.zl52074.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/19 23:59
 */
@EnableDiscoveryClient //开启客户端服务发现
@SpringBootApplication
@MapperScan("com.zl52074.leyou.item.mapper")
public class LyItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class);
    }
}
