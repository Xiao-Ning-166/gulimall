package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * spu查询对象
 *
 * @author xiaoning
 * @date 2023/02/16
 */
@Data
@ApiModel("spu查询对象")
public class SpuQueryDTO {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long catalogId;

    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态。0-新建，1-上架，2-下架")
    private Integer publishStatus;

    /**
     * 搜索关键词
     */
    @ApiModelProperty(value = "搜索关键词")
    private String keyword;

}
