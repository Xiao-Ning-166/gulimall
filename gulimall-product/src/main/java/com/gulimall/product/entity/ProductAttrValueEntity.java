package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * spu属性值
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_product_attr_value")
@ApiModel(value = "spu属性值对象")
public class ProductAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 商品id
	 */
	@ApiModelProperty(value = "商品id")
	private Long spuId;
	/**
	 * 属性id
	 */
	@ApiModelProperty(value = "属性id")
	private Long attrId;
	/**
	 * 属性名
	 */
	@ApiModelProperty(value = "属性名")
	private String attrName;
	/**
	 * 属性值
	 */
	@ApiModelProperty(value = "属性值")
	private String attrValue;
	/**
	 * 顺序
	 */
	@ApiModelProperty(value = "顺序")
	private Integer attrSort;
	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】
	 */
	@ApiModelProperty(value = "快速展示【是否展示在介绍上；0-否 1-是】")
	private Integer quickShow;

}
