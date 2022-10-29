package com.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 跨域配置
 *
 * @author xiaoning
 * @date 2022/10/29
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configSource = new CorsConfiguration();

        // 配置跨域相关信息
        // 本次跨域检测的有效期
        configSource.setMaxAge(3600L);
        // 允许所有请求头
        configSource.addAllowedHeader("*");
        // 允许所有类型的方法
        configSource.addAllowedMethod("*");
        // 允许所有ip地址
        configSource.addAllowedOriginPattern("*");
        // 允许保存cookie
        configSource.setAllowCredentials(true);

        configurationSource.registerCorsConfiguration("/**", configSource);

        return new CorsWebFilter(configurationSource);
    }

}
