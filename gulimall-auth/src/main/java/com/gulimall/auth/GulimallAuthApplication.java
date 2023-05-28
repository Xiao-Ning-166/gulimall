package com.gulimall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
// 配置包扫描，否则common包中的扫描不到
@SpringBootApplication(scanBasePackages = "com.gulimall")
@EnableFeignClients(basePackages = {"com.gulimall.api.**.feign"})
public class GulimallAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallAuthApplication.class, args);
    }

}
