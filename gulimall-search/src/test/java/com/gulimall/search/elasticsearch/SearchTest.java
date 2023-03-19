package com.gulimall.search.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author xiaoning
 * @date 2023/03/02
 */
@SpringBootTest
@DisplayName("搜索文档")
public class SearchTest {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Test
    @DisplayName("查询全部文档 —— 普通方式")
    void testMatchAllQueryNormal() throws IOException {

        // 创建 SearchRequest.Builder 对象
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder().index("account");

        MatchAllQuery matchAllQuery = new MatchAllQuery.Builder().build();

        // 构建 Query 对象
        Query query = new Query.Builder().matchAll(matchAllQuery).build();

        // 创建 request 对象
        SearchRequest searchRequest = searchRequestBuilder.query(query).build();

        // 发送请求，获取响应
        SearchResponse<Map> searchResponse = elasticsearchClient.search(searchRequest, Map.class);

        // 解析响应
        System.out.println(searchResponse.toString());
    }

    @Test
    @DisplayName("查询全部文档 —— 函数式编程方式")
    void testMatchAllQueryFunction() throws IOException {
        // 发送请求，获取响应
        SearchResponse<Map> searchResponse = elasticsearchClient.search(request -> {
            request.index("account")
                    .query(query -> {
                        query.matchAll(matchAllQuery -> {
                            return matchAllQuery;
                        });
                        return query;
                    });

            return request;
        }, Map.class);

        // 解析响应
        System.out.println(searchResponse.toString());

    }
}
