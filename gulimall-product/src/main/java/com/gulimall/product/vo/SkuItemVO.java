package com.gulimall.product.vo;

import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * sku详情
 *
 * @author xiaoning
 * @date 2023/04/22
 */
@Data
@ApiModel(value = "sku详情")
public class SkuItemVO {

    /**
     * 基本信息
     */
    private SkuInfoEntity info;

    /**
     * 图片列表
     */
    private List<SkuImagesEntity> images;

    /**
     * 是否有货
     */
    private Boolean hasStock;

    /**
     * 销售属性
     */
    private List<SpuSaleAttrVO> saleAttrs;

    /**
     * 描述图片
     */
    private SpuInfoDescEntity description;

    /**
     * 规格参数
     */
    private List<SpuBaseAttrVO> skuBaseAttrs;
}
