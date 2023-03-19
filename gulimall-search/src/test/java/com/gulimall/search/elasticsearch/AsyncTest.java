package com.gulimall.search.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author xiaoning
 * @date 2023/03/03
 */
@SpringBootTest
public class AsyncTest {

    private ElasticsearchAsyncClient asyncClient;

    @BeforeEach
    void testAsyncClient() {

        // 1、创建 low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost("192.168.111.201", 9200)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 3、创建 Java API client
        asyncClient = new ElasticsearchAsyncClient(transport);

    }

    @Test
    void name() {

        asyncClient.search(request -> {
            return request.index("account")
                .query(query -> {
                    return query.range(rangeQuery -> {
                        return rangeQuery.field("age")
                                .gt(JsonData.of(20));
                    });
                });
        }, Map.class)
        // 请求完成的回调
        .whenComplete((result, error) -> {
            if (error != null) {
                System.out.println("查询出错");
            } else {
                System.out.println(result.toString());
            }
        });

    }
}
