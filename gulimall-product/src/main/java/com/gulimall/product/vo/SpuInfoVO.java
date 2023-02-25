package com.gulimall.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoning
 * @date 2023/02/16
 */
@Data
public class SpuInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String spuDescription;
    /**
     * 所属分类id
     */
    @ApiModelProperty(value = "所属分类")
    private String catalogName;
    /**
     * 所属分类id
     */
    @ApiModelProperty(value = "所属分类")
    private String catalogId;
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌")
    private String brandName;
    /**
     * 上架状态[0-新增，1-上架，2-下架]
     */
    @ApiModelProperty(value = "上架状态[0-新增，1-上架，2-下架]")
    private Integer publishStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:dd:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:dd:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
