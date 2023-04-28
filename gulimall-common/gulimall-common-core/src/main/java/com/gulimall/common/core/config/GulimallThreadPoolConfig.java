package com.gulimall.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author xiaoning
 * @date 2023/04/25
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(GulimallThreadPoolConfigProperties.class)
public class GulimallThreadPoolConfig {


    @Bean
    public ThreadPoolExecutor bizThreadPoolExecutor(GulimallThreadPoolConfigProperties configProperties) {
        log.info("开始配置线程池===========");
        return new ThreadPoolExecutor(
                configProperties.getCorePoolSize(),
                configProperties.getMaxPoolSize(),
                configProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(configProperties.getWorkQueueSize()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
