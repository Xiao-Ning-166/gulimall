package com.gulimall.oos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GulimallModuleOosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallModuleOosApplication.class, args);
    }

}
