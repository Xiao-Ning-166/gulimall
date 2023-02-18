package com.gulimall.api.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * sku满减信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@Data
public class SkuFullReductionDTO {

    /**
     * spu_id
     */
    private Long skuId;

    /**
     * 满多少
     */
    private BigDecimal fullPrice;

    /**
     * 减多少
     */
    private BigDecimal reducePrice;

    /**
     * 是否参与其他优惠
     */
    private Integer addOther;

}
