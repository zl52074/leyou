package com.zl52074.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/13 15:57
 */
@EnableFeignClients
@EnableDiscoveryClient //开启客户端服务发现
@SpringBootApplication
public class LySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LySearchApplication.class);
    }
}
