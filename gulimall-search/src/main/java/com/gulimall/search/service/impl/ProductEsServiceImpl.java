package com.gulimall.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.common.core.exception.GulimallException;
import com.gulimall.search.contanst.ElasticContanst;
import com.gulimall.search.service.ProductEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/03/15
 */
@Slf4j
@Service
public class ProductEsServiceImpl implements ProductEsService {

    private final ElasticsearchClient elasticsearchClient;

    public ProductEsServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    /**
     * 商品上架
     *
     * @param productPutawayEsDTOs
     */
    @Override
    public boolean putaway(List<ProductPutawayEsDTO> productPutawayEsDTOs) throws IOException {
        // 判断索引是否存在
        BooleanResponse booleanResponse = elasticsearchClient.indices().exists(request -> request.index(ElasticContanst.PRODUCT_INDEX));
        boolean indexExists = booleanResponse.value();
        if (!indexExists) {
            // 创建索引
            elasticsearchClient.indices().create(request -> {
                return request.index(ElasticContanst.PRODUCT_INDEX)
                    .mappings(mappings -> {
                        try {
                            return mappings.withJson(new ClassPathResource("mapping/product-mapping.json").getInputStream());
                        } catch (IOException e) {
                            log.error("未找到mapping映射文件或者mapping映射文件有问题");
                        }
                        throw new GulimallException("未找到mapping映射文件或者mapping映射文件有问题");
                    });
            });
        }

        if (CollectionUtil.isEmpty(productPutawayEsDTOs)) {
            return true;
        }

        // 添加文档
        BulkResponse bulkResponse = elasticsearchClient.bulk(request -> {
            for (ProductPutawayEsDTO productPutawayEsDTO : productPutawayEsDTOs) {
                request.operations(bulkOperation -> {
                    bulkOperation.create(createOperation -> {
                        createOperation.index(ElasticContanst.PRODUCT_INDEX)
                                .id(productPutawayEsDTO.getSkuId())
                                .document(productPutawayEsDTO);
                        return createOperation;
                    });
                    return bulkOperation;
                });
            }
            return request;
        });

        // 解析结果
        boolean errors = bulkResponse.errors();
        if (errors) {
            log.error("es添加索引过程中出错。" + bulkResponse.toString());
            throw new GulimallException("es添加索引过程中出错。详细信息" + bulkResponse.toString());
        }

        // IndexResponse indexResponse = elasticsearchClient.index(request -> {
        //     return request.index(ElasticContanst.PRODUCT_INDEX)
        //             .id(productPutawayEsDTO.getSkuId())
        //             .document(productPutawayEsDTO);
        // });
        // 解析结果
        // if (!indexResponse.result().equals(Result.Created)) {
        //     log.error("es添加索引过程中出错。" + indexResponse.toString());
        //     throw new GulimallException("es添加索引过程中出错。" + indexResponse.toString());
        // }

        return true;
    }
}
