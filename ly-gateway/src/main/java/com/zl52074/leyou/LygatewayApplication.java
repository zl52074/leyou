package com.zl52074.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/19 22:36
 */
@EnableZuulProxy //启用zuul
@SpringBootApplication
public class LygatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LygatewayApplication.class);
    }
}
