package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品三级分类
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_category")
@ApiModel(value = "分类对象")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long catId;
	/**
	 * 分类名称
	 */
	@ApiModelProperty(value = "分类名称")
	private String name;
	/**
	 * 父分类id
	 */
	@ApiModelProperty(value = "父分类id")
	private Long parentCid;
	/**
	 * 父分类名称
	 */
	@ApiModelProperty(value = "父分类名称")
	@TableField(exist = false)
	private String parentName;
	/**
	 * 层级
	 */
	@ApiModelProperty(value = "分类层级")
	private Integer catLevel;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@ApiModelProperty(value = "是否显示[0-不显示，1显示]")
	private Integer showStatus;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 图标地址
	 */
	@ApiModelProperty(value = "分类图标")
	private String icon;
	/**
	 * 计量单位
	 */
	@ApiModelProperty(value = "计量单位")
	private String productUnit;
	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	private Integer productCount;

	/**
	 * 分类的子分类集合
	 */
	@ApiModelProperty(value = "分类的子分类集合")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private List<CategoryEntity> children;

	/**
	 * 是否删除。0-未删除 1-已删除
	 */
	@ApiModelProperty(value = "是否删除。0-未删除 1-已删除")
	@TableLogic(value = "0", delval = "1")
	private Integer isDelete;

}
