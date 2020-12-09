package com.zl52074.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/3 14:21
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LyPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyPageApplication.class );
    }
}
