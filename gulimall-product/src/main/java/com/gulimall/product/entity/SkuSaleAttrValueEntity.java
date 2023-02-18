package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sku销售属性&值
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_sku_sale_attr_value")
@ApiModel(value = "sku销售属性&值对象")
public class SkuSaleAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * sku_id
	 */
	@ApiModelProperty(value = "sku id")
	private Long skuId;
	/**
	 * attr_id
	 */
	@ApiModelProperty(value = "属性id")
	private Long attrId;
	/**
	 * 销售属性名
	 */
	@ApiModelProperty(value = "销售属性名")
	private String attrName;
	/**
	 * 销售属性值
	 */
	@ApiModelProperty(value = "销售属性值")
	private String attrValue;
	/**
	 * 顺序
	 */
	@ApiModelProperty(value = "顺序")
	private Integer attrSort;

}
