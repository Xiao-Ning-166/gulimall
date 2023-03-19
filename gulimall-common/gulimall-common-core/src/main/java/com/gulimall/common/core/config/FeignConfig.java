package com.gulimall.common.core.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign配置类
 *
 * @author xiaoning
 * @date 2023/03/17
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level level() {
        return Logger.Level.BASIC;
    }

    // @Bean
    // public OkHttpClient okHttpClient() {
    //     OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
    //             // 连接超时
    //             .connectTimeout(1, TimeUnit.MINUTES)
    //             // 读取超时
    //             .readTimeout(1, TimeUnit.MINUTES)
    //             .build();
    //
    //     return okHttpClient;
    // }
}
