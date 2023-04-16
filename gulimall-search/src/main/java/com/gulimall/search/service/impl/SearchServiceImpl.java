package com.gulimall.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.search.contanst.ElasticContanst;
import com.gulimall.search.model.dto.SearchParamDTO;
import com.gulimall.search.model.vo.AttrVO;
import com.gulimall.search.model.vo.BrandVO;
import com.gulimall.search.model.vo.CategoryVO;
import com.gulimall.search.model.vo.ProductSearchResultVO;
import com.gulimall.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaoning
 * @date 2023/04/08
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 查询
     *
     * @param searchParamDTO
     * @return
     */
    @Override
    public ProductSearchResultVO search(SearchParamDTO searchParamDTO) {
        ProductSearchResultVO productSearchResultVO = null;

        try {
            // 构建请求
            SearchResponse<Map> searchResponse = elasticsearchClient.search((request) -> {
                request.index(ElasticContanst.PRODUCT_INDEX)
                    // 查询条件
                    .query((query) -> {
                        return query.bool(bool -> {
                            // 全文匹配关键词
                            if (StrUtil.isNotBlank(searchParamDTO.getKeyword())) {
                                bool.must(must -> {
                                    return must.match(match -> {
                                        return match.field("skuTitle")
                                                .query(searchParamDTO.getKeyword());
                                    });
                                });
                            }
                            // 过滤条件
                            // 分类
                            if (ObjectUtil.isNotNull(searchParamDTO.getCatalog3Id())) {
                                bool.filter(filter -> {
                                    return filter.term(term -> {
                                        return term.field("catalogId")
                                                .value(searchParamDTO.getCatalog3Id());
                                    });
                                });
                            }
                            // 品牌
                            if (CollectionUtil.isNotEmpty(searchParamDTO.getBrandId())) {
                                bool.filter(filter -> {
                                    return filter.terms(termsQuery -> {
                                        return termsQuery.field("brandId")
                                            .terms(termsQueryField -> {
                                                List<FieldValue> fieldValues = searchParamDTO.getBrandId().stream().map(brandId -> {
                                                    FieldValue fieldValue = new FieldValue.Builder().longValue(brandId).build();
                                                    return fieldValue;
                                                }).collect(Collectors.toList());
                                                termsQueryField.value(fieldValues);
                                                return termsQueryField;
                                            });
                                    });
                                });
                            }
                            // 属性
                            if (CollectionUtil.isNotEmpty(searchParamDTO.getAttrs())) {
                                bool.filter(filter -> {
                                    for (String attrStr : searchParamDTO.getAttrs()) {
                                        List<String> strings = StrUtil.splitTrim(attrStr, "_");
                                        String attrId = strings.get(0);
                                        List<String> attrValues = StrUtil.splitTrim(strings.get(1), ":");
                                        filter.nested(nestedQuery -> {
                                            return nestedQuery.path("attrs")
                                                .query(query1 -> {
                                                    return query1.bool(nestedBoolQuery -> {
                                                        nestedBoolQuery.must(nestedMustQuery -> {
                                                            nestedMustQuery.term(nestedTermQuery -> {
                                                                return nestedTermQuery.field("attrs.attrId")
                                                                        .value(attrId);
                                                            });
                                                            nestedMustQuery.termsSet(nestedTermsSetQuery -> {
                                                                return nestedTermsSetQuery.field("attrs.attrValue")
                                                                        .terms(attrValues);
                                                            });
                                                            return nestedMustQuery;
                                                        });
                                                        return nestedBoolQuery;
                                                    });
                                                })
                                                .scoreMode(ChildScoreMode.None);
                                        });
                                    }
                                    return filter;
                                });
                            }
                            // 过滤出有库存的
                            if (ObjectUtil.isNotNull(searchParamDTO.getHasStock())) {
                                bool.filter(filter -> {
                                    return filter.term(termQuery -> {
                                        return termQuery.field("hasStock")
                                            .value(Boolean.TRUE);
                                    });
                                });
                            }
                            // 价格区间
                            if (StrUtil.isNotBlank(searchParamDTO.getSkuPrice())) {
                                bool.filter(filter -> {
                                    return filter.range(rangeQuery -> {
                                        rangeQuery.field("skuPrice");
                                        // minPrice_maxPrice
                                        String skuPriceStr = searchParamDTO.getSkuPrice();
                                        List<String> strs = StrUtil.splitTrim(skuPriceStr, "_");
                                        if (strs.size() == 2) {
                                            rangeQuery.gte(JsonData.fromJson(strs.get(0)))
                                                    .lte(JsonData.fromJson(strs.get(1)));
                                        } else if (strs.size() == 1 && skuPriceStr.startsWith("_")) {
                                            rangeQuery.lte(JsonData.fromJson(strs.get(0)));
                                        } else if (strs.size() == 1 && skuPriceStr.endsWith("_")) {
                                            rangeQuery.gte(JsonData.fromJson(strs.get(0)));
                                        }
                                        return rangeQuery;
                                    });
                                });
                            }
                            return bool;
                        });
                    });
                    // 排序
                    if (StrUtil.isNotBlank(searchParamDTO.getSort())) {
                        List<String> strings = StrUtil.splitTrim(searchParamDTO.getSort(), "_");
                        String sortField = strings.get(0);
                        String sortType = strings.get(1);
                        request.sort(sort -> {
                            return sort.field(fieldSort -> {
                                return fieldSort.field(sortField)
                                        .order("asc".equals(sortType) ? SortOrder.Asc : SortOrder.Desc);
                            });
                        });
                    }
                    // 分页设置
                    if (ObjectUtil.isNull(searchParamDTO.getCurrent())) {
                        searchParamDTO.setCurrent(1);
                    }
                    request.from((ObjectUtil.isNotNull(searchParamDTO.getCurrent()) && searchParamDTO.getCurrent() > 0 ? searchParamDTO.getCurrent() - 1 : 0) * ElasticContanst.PRODUCT_DEFAULT_PAGE_SIZE)
                           .size(ElasticContanst.PRODUCT_DEFAULT_PAGE_SIZE);
                    // 高亮设置
                    if (StrUtil.isNotBlank(searchParamDTO.getKeyword())) {
                        request.highlight(highlight -> {
                            return highlight.fields("skuTitle", highlightField -> {
                                return highlightField.preTags("<b style='color:red'>")
                                        .postTags("</b>");
                            });
                        });
                    }

                // 聚合
                // 品牌聚合
                request.aggregations("brand_agg", brandAggregation -> {
                    return brandAggregation.terms(termsAggregation -> {
                        return termsAggregation.field("brandId").size(50);
                    })
                    .aggregations("brand_name_agg", brandNameAggregation -> {
                        return brandNameAggregation.terms(t -> {
                            return t.field("brandName");
                        });
                    })
                    .aggregations("brand_img_agg", brandImageAggregation -> {
                        return brandImageAggregation.terms(t -> {
                            return t.field("brandImg");
                        });
                    });
                })
                // 分类聚合
                .aggregations("catalog_agg", catalogAggregation -> {
                    return catalogAggregation.terms(termsAggregation -> {
                        return termsAggregation.field("catalogId").size(50);
                    })
                    .aggregations("catalog_name_agg", catalogNameAggregation -> {
                        return catalogNameAggregation.terms(termsAggregation -> {
                            return termsAggregation.field("catalogName").size(50);
                        });
                    });
                })
                .aggregations("attr_agg", attrAggregation -> {
                    return attrAggregation.nested(nestedAggregation -> {
                        return nestedAggregation.path("attrs");
                    })
                    .aggregations("attr_id_agg", attrIdAggregation -> {
                        return attrIdAggregation.terms(termsAggregation -> {
                            return termsAggregation.field("attrs.attId").size(50);
                        })
                        .aggregations("attr_name_agg", attrNameAggregation -> {
                            return attrNameAggregation.terms(termsAggregation -> {
                                return termsAggregation.field("attrs.attrName");
                            });
                        }).aggregations("attr_value_agg", attrValueAggregation -> {
                            return attrValueAggregation.terms(termsAggregation -> {
                                return termsAggregation.field("attrs.attrValue").size(50);
                            });
                        });
                    });

                });

                log.info("es查询DSL语句： {}", request.toString());
                return request;
            }, Map.class);

            log.info("es查询响应结果：{}", searchResponse.toString());
            // 解析响应，封装结果
            productSearchResultVO = analyzeResponse(searchParamDTO, searchResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return productSearchResultVO;
    }

    /**
     * 解析响应
     *
     * @param searchParamDTO
     * @param searchResponse
     * @return
     */
    private ProductSearchResultVO analyzeResponse(SearchParamDTO searchParamDTO, SearchResponse<Map> searchResponse) {
        ProductSearchResultVO productSearchResultVO = new ProductSearchResultVO();

        HitsMetadata<Map> hits = searchResponse.hits();
        // 总页码
        long total = hits.total().value();
        int totalPages = NumberUtil.ceilDiv(Math.toIntExact(total), ElasticContanst.PRODUCT_DEFAULT_PAGE_SIZE);
        productSearchResultVO.setTotal(totalPages);
        productSearchResultVO.setCurrent(searchParamDTO.getCurrent());
        // 商品列表
        List<ProductPutawayEsDTO> products = new ArrayList<>();
        List<Hit<Map>> hitsResult = hits.hits();
        if (CollectionUtil.isNotEmpty(hitsResult)) {
            for (Hit<Map> hit : hitsResult) {
                Map source = hit.source();
                ProductPutawayEsDTO productPutawayEsDTO = BeanUtil.mapToBean(source, ProductPutawayEsDTO.class, false);
                Map<String, List<String>> highlight = hit.highlight();
                List<String> skuTitleHighlight = highlight.get("skuTitle");
                if (CollectionUtil.isEmpty(skuTitleHighlight)) {
                    products.add(productPutawayEsDTO);
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                for (String s : skuTitleHighlight) {
                    sb.append(s);
                }
                productPutawayEsDTO.setSkuTitle(sb.toString());
                products.add(productPutawayEsDTO);
            }
        }
        productSearchResultVO.setProducts(products);

        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        // 品牌
        LongTermsAggregate brandAgg = aggregations.get("brand_agg").lterms();
        Buckets<LongTermsBucket> brandAggBuckets = brandAgg.buckets();
        List<BrandVO> brands = new ArrayList<>();
        for (LongTermsBucket longTermsBucket : brandAggBuckets.array()) {
            BrandVO brandVO = new BrandVO();
            // 品牌id
            String brandId = longTermsBucket.key();
            brandVO.setBrandId(brandId);
            // 品牌图片
            Map<String, Aggregate> aggregations1 = longTermsBucket.aggregations();
            StringTermsAggregate brandImgAgg = aggregations1.get("brand_img_agg").sterms();
            StringTermsBucket stringTermsBucket = brandImgAgg.buckets().array().get(0);
            brandVO.setBrandImg(stringTermsBucket.key().stringValue());
            // 品牌名称
            StringTermsAggregate brandNameAgg = aggregations1.get("brand_name_agg").sterms();
            StringTermsBucket brandNameStringTermsBucket = brandNameAgg.buckets().array().get(0);
            brandVO.setBrandName(brandNameStringTermsBucket.key().stringValue());

            brands.add(brandVO);
        }
        productSearchResultVO.setBrands(brands);

        // 分类
        LongTermsAggregate catalogAgg = aggregations.get("catalog_agg").lterms();
        List<CategoryVO> categories = new ArrayList<>();
        for (LongTermsBucket longTermsBucket : catalogAgg.buckets().array()) {
            CategoryVO categoryVO = new CategoryVO();
            // 分类id
            String catalogId = longTermsBucket.key();
            categoryVO.setCatalogId(catalogId);
            // 分类名称
            Map<String, Aggregate> subAgg = longTermsBucket.aggregations();
            StringTermsAggregate catalogNameAgg = subAgg.get("catalog_name_agg").sterms();
            StringTermsBucket catalogNameBucket = catalogNameAgg.buckets().array().get(0);
            categoryVO.setCatalogName(catalogNameBucket.key().stringValue());

            categories.add(categoryVO);
        }
        productSearchResultVO.setCategories(categories);

        // 属性
        NestedAggregate attrAgg = aggregations.get("attr_agg").nested();
        List<AttrVO> attrs = new ArrayList<>();
        StringTermsAggregate attrIdAgg = attrAgg.aggregations().get("attr_id_agg").sterms();
        for (StringTermsBucket stringTermsBucket : attrIdAgg.buckets().array()) {
            AttrVO attrVO = new AttrVO();
            // 属性id
            attrVO.setAttrId(stringTermsBucket.key().stringValue());
            Map<String, Aggregate> aggregations1 = stringTermsBucket.aggregations();
            // aggregations1.get("attr_name_agg")
        }
        productSearchResultVO.setAttrs(attrs);

        return productSearchResultVO;
    }
}
