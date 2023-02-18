package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_category_brand_relation")
@ApiModel(value = "品牌分类关联对象")
public class CategoryBrandRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 品牌id
	 */
	@ApiModelProperty(value = "品牌id")
	private Long brandId;
	/**
	 * 分类id
	 */
	@ApiModelProperty(value = "分类id")
	private Long catelogId;

	/**
	 * 分类名称
	 */
	@ApiModelProperty(value = "分类名称")
	@TableField(exist = false)
	private String catelogName;

}
