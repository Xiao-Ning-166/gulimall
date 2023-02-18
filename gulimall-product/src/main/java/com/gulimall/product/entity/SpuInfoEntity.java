package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * spu基本信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_spu_info")
@ApiModel(value = "spu基本信息对象")
public class SpuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
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
	@ApiModelProperty(value = "所属分类id")
	private Long catalogId;
	/**
	 * 品牌id
	 */
	@ApiModelProperty(value = "品牌id")
	private Long brandId;
	/**
	 * 重量
	 */
	@ApiModelProperty(value = "重量")
	private BigDecimal weight;
	/**
	 * 上架状态[0 - 下架，1 - 上架]
	 */
	@ApiModelProperty(value = "上架状态[0 - 下架，1 - 上架]")
	private Integer publishStatus;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

}
