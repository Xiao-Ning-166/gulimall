package com.gulimall.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaoning
 * @date 2023/04/25
 */
@Data
@ConfigurationProperties(prefix = "gulimall.thread")
public class GulimallThreadPoolConfigProperties {

    /**
     * 核心线程数
     */
    private Integer corePoolSize = 50;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize = 100;

    /**
     * 存活时间。单位：秒
     */
    private Long keepAliveTime = 100L;

    /**
     * 工作队列长度
     */
    private Integer workQueueSize = 100;

}
