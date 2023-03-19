package com.gulimall.api.search.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品上架对象
 *
 * @author xiaoning
 * @date 2023/03/13
 */
@Data
@ApiModel(value = "商品上架对象")
public class ProductPutawayEsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sku ID
     */
    private String skuId;

    /**
     * spu id
     */
    private String spuId;

    /**
     * sku标题
     */
    private String skuTitle;

    /**
     * sku价格
     */
    private BigDecimal skuPrice;

    /**
     * sku默认图片地址
     */
    private String skuImg;

    /**
     * 销量
     */
    private Long saleCount;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

    /**
     * 热点分数
     */
    private Integer hotScore;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 分类id
     */
    private String catalogId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 分类名称
     */
    private String catalogName;

    /**
     * 名称图片
     */
    private String brandImg;

    private List<ProductAttrEsDTO> attrs;

}
