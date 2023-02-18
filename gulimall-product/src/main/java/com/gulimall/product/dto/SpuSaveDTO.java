package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * spu保存对象
 *
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
@ApiModel("spu对象")
public class SpuSaveDTO {

    /**
     * spu名称
     */
    private String spuName;

    /**
     * spu描述
     */
    private String spuDescription;

    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    @ApiModelProperty(value = "分类id", required = true)
    private Long catelogId;

    /**
     * 品牌id
     */
    @NotNull(message = "品牌id不能为空")
    @ApiModelProperty(value = "品牌id", required = true)
    private Long brandId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    private Integer publishStatus;

    /**
     * 描述图片地址
     */
    private List<String> decript;

    /**
     * 图集
     */
    private List<String> images;

    /**
     * 积分信息
     */
    private SpuBoundsDTO bounds;

    /**
     * 规格参数信息
     */
    private List<SpuBaseAttrDTO> baseAttrs;

    /**
     * sku信息
     */
    private List<SpuSkuDTO> skus;

}
