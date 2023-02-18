package com.gulimall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoning
 * @date 2023/02/17
 */
@Data
public class SkuInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * skuId
     */
    @ApiModelProperty(value = "主键")
    private String skuId;
    /**
     * sku名称
     */
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    /**
     * 所属分类id
     */
    @ApiModelProperty(value = "所属分类")
    private String catalogName;
    /**
     * 品牌名
     */
    @ApiModelProperty(value = "品牌名")
    private String brandName;
    /**
     * 默认图片
     */
    @ApiModelProperty(value = "默认图片")
    private String skuDefaultImg;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String skuTitle;
    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String skuSubtitle;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Long saleCount;

}
