package com.gulimall.oos.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置类
 *
 * @author xiaoning
 * @date 2022/11/19
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    private final MinioProperties minioProperties;

    public MinioConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                // minio服务地址
                .endpoint(this.minioProperties.getEndpoint())
                // minio账号密码
                .credentials(this.minioProperties.getAccessKey(), this.minioProperties.getSecretKey())
                .build();
    }

}
