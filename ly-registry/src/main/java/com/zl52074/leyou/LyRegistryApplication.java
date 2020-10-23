package com.zl52074.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/10/19 22:13
 */
@EnableEurekaServer
@SpringBootApplication
public class LyRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyRegistryApplication.class);
    }
}
