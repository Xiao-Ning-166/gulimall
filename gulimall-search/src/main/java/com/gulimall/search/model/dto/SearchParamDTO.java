package com.gulimall.search.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Elasticsearch查询参数
 *
 * @author xiaoning
 * @date 2023/04/08
 */
@Data
public class SearchParamDTO {

    /**
     * 查询关键词
     */
    private String keyword;

    /**
     * 分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件
     */
    private String sort;

    /**
     * 是否仅显示有货
     */
    private Integer hasStock;

    /**
     * 价格区间
     */
    private String skuPrice;

    /**
     * 品牌id
     */
    private List<Long> brandId;

    /**
     * 属性条件
     */
    private List<String> attrs;

    /**
     * 当前页码
     */
    private Integer current;
}
