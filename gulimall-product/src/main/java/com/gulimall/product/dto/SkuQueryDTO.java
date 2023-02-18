package com.gulimall.product.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaoning
 * @date 2023/02/17
 */
@Data
public class SkuQueryDTO {

    /**
     * 分类id
     */
    private Long catalogId;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 最低价
     */
    private BigDecimal minPrice;

    /**
     * 最高价
     */
    private BigDecimal maxPrice;

    /**
     * 关键词
     */
    private String keyword;

}
