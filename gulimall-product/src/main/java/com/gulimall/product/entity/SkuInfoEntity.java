package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_sku_info")
@ApiModel(value = "sku信息对象")
public class SkuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * skuId
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
	private Long skuId;
	/**
	 * spuId
	 */
	@ApiModelProperty(value = "关联的spu id")
	private Long spuId;
	/**
	 * sku名称
	 */
	@ApiModelProperty(value = "sku名称")
	private String skuName;
	/**
	 * sku介绍描述
	 */
	@ApiModelProperty(value = "sku介绍描述")
	private String skuDesc;
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
