package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
@ApiModel("sku会员价格对象")
public class SkuMemberPriceDTO {

    private Long id;

    /**
     * 会员等级名称
     */
    private String name;

    /**
     * 会员价格
     */
    private BigDecimal price;

}
