package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性分组
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_attr_group")
@ApiModel(value = "属性分组对象")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long attrGroupId;
	/**
	 * 组名
	 */
	@ApiModelProperty(value = "属性分组名")
	private String attrGroupName;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String descript;
	/**
	 * 组图标
	 */
	@ApiModelProperty(value = "属性分组图标")
	private String icon;
	/**
	 * 所属分类id
	 */
	@ApiModelProperty(value = "所属分类id")
	private Long catelogId;

}
