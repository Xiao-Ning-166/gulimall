package com.gulimall.api.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * sku会员价格信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@Data
public class MemberPriceDTO {

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 会员等级名
     */
    private String memberLevelName;

    /**
     * 会员对应价格
     */
    private BigDecimal memberPrice;

    /**
     * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
     */
    private Integer addOther;

}
