package com.gulimall.search.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.ElasticsearchCatClient;
import co.elastic.clients.elasticsearch.cat.HealthResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author xiaoning
 * @date 2023/02/28
 */
@SpringBootTest
public class JavaAPIClientTest {

    private static ElasticsearchClient elasticsearchClient;

    @BeforeAll
    static void beforeAll() {
        // 1、创建 low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost("192.168.111.201", 9200)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 3、创建 Java API client
        elasticsearchClient = new ElasticsearchClient(transport);
    }

    @Test
    void test01() throws IOException {

        ElasticsearchCatClient cat = elasticsearchClient.cat();
        HealthResponse health = cat.health();
        System.out.println(health.toString());

    }
}
