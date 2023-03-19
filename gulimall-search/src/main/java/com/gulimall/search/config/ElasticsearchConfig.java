package com.gulimall.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elasticsearc java api client配置类
 *
 * @author xiaoning
 * @date 2023/02/28
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        log.info("初始化 Elasticsearch Java API Client");
        // 1、创建 low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 3、 API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }

}
