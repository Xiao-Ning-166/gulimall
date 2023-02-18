package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_attr")
@ApiModel(value = "属性实体类")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long attrId;
	/**
	 * 属性名
	 */
	@ApiModelProperty(value = "属性名称")
	private String attrName;
	/**
	 * 是否需要检索[0-不需要，1-需要]
	 */
	@ApiModelProperty(value = "是否需要检索【0-不需要，1-需要】")
	private Integer searchType;
	/**
	 * 属性图标
	 */
	@ApiModelProperty(value = "属性图标")
	private String icon;
	/**
	 * 可选值列表[用逗号分隔]
	 */
	@ApiModelProperty(value = "可选值列表")
	private String valueSelect;
	/**
	 * 是否多选【0-单选；1-多选】
	 */
	@ApiModelProperty(value = "是否多选【0-单选；1-多选】")
	private Integer isMultiple;
	/**
	 * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
	 */
	@ApiModelProperty(value = "属性类型【0-销售属性，1-基本属性】")
	private Integer attrType;
	/**
	 * 启用状态[0 - 禁用，1 - 启用]
	 */
	@ApiModelProperty(value = "属性类型【0-禁用，1-启用】")
	private Long enable;
	/**
	 * 所属分类
	 */
	@ApiModelProperty(value = "所属分类id")
	private Long catelogId;
	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
	 */
	@ApiModelProperty(value = "快速展示【是否展示在介绍上；0-否 1-是】")
	private Integer showDesc;

}
