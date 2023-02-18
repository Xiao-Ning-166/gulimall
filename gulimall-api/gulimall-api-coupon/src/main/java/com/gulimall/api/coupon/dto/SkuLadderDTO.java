package com.gulimall.api.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * sku满多少件打多少折信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@Data
public class SkuLadderDTO {

    /**
     * spu_id
     */
    private Long skuId;

    /**
     * 满几件
     */
    private Integer fullCount;

    /**
     * 打几折
     */
    private BigDecimal discount;

    /**
     * 折后价
     */
    private BigDecimal price;

    /**
     * 是否叠加其他优惠[0-不可叠加，1-可叠加]
     */
    private Integer addOther;

}
