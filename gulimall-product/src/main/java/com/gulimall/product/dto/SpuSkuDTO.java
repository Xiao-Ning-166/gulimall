package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
@ApiModel(value = "sku对象")
public class SpuSkuDTO {

    private List<SkuAttrDTO> attr;

    private String skuName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 主标题
     */
    private String skuTitle;

    /**
     * 副标题
     */
    private String skuSubtitle;

    /**
     * sku图片信息
     */
    private List<SkuImageDTO> images;

    private List<String> descar;

    /**
     * 满多少减打折条件
     */
    private Integer fullCount;

    /**
     * 满多少减打折力度
     */
    private BigDecimal discount;

    /**
     * 是否可以叠加其他优惠
     */
    private Integer countStatus;

    /**
     * 满减满足金额
     */
    private BigDecimal fullPrice;

    /**
     * 满减减去金额
     */
    private BigDecimal reducePrice;

    private Integer priceStatus;

    /**
     * 会员价格信息
     */
    private List<SkuMemberPriceDTO> memberPrice;

}
