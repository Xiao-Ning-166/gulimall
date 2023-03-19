package com.gulimall.search.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author xiaoning
 * @date 2023/03/01
 */
@DisplayName("索引操作")
@SpringBootTest
public class IndexTest {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引 —— 普通方式
     */
    @Test
    @DisplayName("创建索引 —— 普通方式")
    void testCreateIndexNormal() throws IOException {
        // 1、创建Request对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index("creat_index_01").build();
        // 2、发送请求，获取响应
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(createIndexRequest);
        System.out.println("索引是否创建成功" + createIndexResponse.acknowledged());
    }

    @Test
    @DisplayName("创建索引 —— 函数式编程方式")
    void testCreateIndexFunction() throws IOException {
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(p -> {
            return p.index("create_index_02");
        });

        System.out.println("索引是否创建成功：" + createIndexResponse.acknowledged());
    }

    @Test
    @DisplayName("查询索引 —— 普通方式")
    void testGetIndexNormal() throws IOException {
        // 1、创建 request 对象
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("creat_index_01").build();
        // 2、发送请求获取响应
        GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(getIndexRequest);
        System.out.println(getIndexResponse.toString());
    }

    @Test
    @DisplayName("查询索引 —— 函数式编程方式")
    void testGetIndexFunction() throws IOException {
        // 发送请求，获取响应
        GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(request -> {
            return request.index("create_index_02");
        });

        System.out.println(getIndexResponse.toString());
    }

    @Test
    @DisplayName("删除索引 —— 普通方式")
    void testDeleteIndexNormal() throws IOException {
        // 1、创建 request 对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder()
                .index("creat_index_01").build();
        // 2、发送请求，获取响应
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices()
                .delete(deleteIndexRequest);
        System.out.println(deleteIndexResponse.toString());
    }

    @Test
    @DisplayName("删除索引 —— 函数式编程方式")
    void testDeleteIndexFunction() throws IOException {
        // 发送请求，获取响应
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices().delete(request -> {
            return request.index("create_index_02");
        });

        System.out.println(deleteIndexResponse.toString());
    }
}
