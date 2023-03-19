package com.gulimall.search.elasticsearch;

import cn.hutool.core.util.RandomUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import com.gulimall.search.elasticsearch.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/03/02
 */
@DisplayName("文档操作")
@SpringBootTest
public class DocTest {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Test
    @DisplayName("创建/更新文档 —— 普通方式")
    void testCreateDocNormal() throws IOException {

        User tom = new User(1,"tom", 12);

        // 1、创建 request 对象
        IndexRequest indexRequest = new IndexRequest.Builder()
                // 索引名称
                .index("test_doc")
                .id(tom.getId().toString())
                .document(tom)
                .build();

        // 2、发送请求，获取响应
        IndexResponse indexResponse = elasticsearchClient.index(indexRequest);

        System.out.println(indexResponse.toString());
    }

    @Test
    @DisplayName("创建/更新文档 —— 函数式编程方式")
    void testCreateDocFunction() throws IOException {
        User jerry = new User(2, "jerry", 20);

        // 发送请求，获取响应
        IndexResponse indexResponse = elasticsearchClient.index(request -> {
            return request.index("test_doc")
                    .id(jerry.getId().toString())
                    .document(jerry);
        });

        // 解析响应
        System.out.println(indexResponse.toString());
    }

    @Test
    @DisplayName("按id删除文档 —— 普通方式")
    void testUpdateDocNormal() throws IOException {

        // 1、创建 request 对象
        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index("test_doc")
                .id("1")
                .build();

        // 2、发送请求，获取响应
        DeleteResponse deleteResponse = elasticsearchClient.delete(deleteRequest);

        // 3、解析响应
        System.out.println(deleteResponse.toString());
    }

    @Test
    @DisplayName("按id删除文档 —— 函数式编程方式")
    void testDeleteDocFunction() throws IOException {
        // 发送请求，获取响应
        DeleteResponse deleteResponse = elasticsearchClient.delete(request -> {
            return request.index("test_doc")
                    .id("2");
        });

        // 解析响应
        System.out.println(deleteResponse.toString());
    }

    @Test
    @DisplayName("批量添加文档 —— 普通方式")
    void testBulkAddDocNormal() throws IOException {
        List<BulkOperation> operations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CreateOperation.Builder builder = new CreateOperation.Builder();
            builder.index("test_doc");
            builder.id("101" + i);
            builder.document(new User(100 + i, "100" + i, 18));

            CreateOperation<Object> createOperation = builder.build();

            BulkOperation bulkOperation = new BulkOperation.Builder().create(createOperation).build();

            operations.add(bulkOperation);
        }

        // 创建 request 请求
        BulkRequest bulkRequest = new BulkRequest.Builder()
                .operations(operations)
                .build();

        // 发送请求，获取响应
        BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest);

        // 解析响应
        System.out.println(bulkResponse.toString());
    }

    @Test
    @DisplayName("批量添加文档 —— 函数式编程方式")
    void testBulkAddDocFunction() throws IOException {
        // 发送请求，获取响应
        BulkResponse bulkResponse = elasticsearchClient.bulk(request -> {
            for (int i = 0; i < 5; i++) {
                request.operations(bulkOperation -> {
                    bulkOperation.create(createOperation -> {
                        createOperation.index("test_doc")
                                .id("200" + RandomUtil.randomInt(100))
                                .document(new User(200 + RandomUtil.randomInt(100), "200" + RandomUtil.randomInt(100), 20));

                        return createOperation;
                    });

                    return bulkOperation;
                });
            }
            return request;
        });

        // 解析响应
        System.out.println(bulkResponse.toString());
    }
}
