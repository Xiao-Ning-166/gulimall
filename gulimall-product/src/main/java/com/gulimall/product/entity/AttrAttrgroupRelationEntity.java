package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性&属性分组关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Data
@TableName("pms_attr_attrgroup_relation")
@ApiModel(value = "属性&属性分组关联对象")
public class AttrAttrgroupRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 属性id
	 */
	@ApiModelProperty(value = "属性id")
	private Long attrId;
	/**
	 * 属性分组id
	 */
	@ApiModelProperty(value = "属性分组id")
	private Long attrGroupId;
	/**
	 * 属性组内排序
	 */
	@ApiModelProperty(value = "属性组内排序")
	private Integer attrSort;

}
