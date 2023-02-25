package com.gulimall.storage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Data
@TableName("wms_ware_info")
@ApiModel(value = "仓库信息实体类")
public class WareInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private String id;

	/**
	 * 仓库名
	 */
	@ApiModelProperty(value = "仓库名")
	private String name;

	/**
	 * 仓库地址
	 */
	@ApiModelProperty(value = "仓库地址")
	private String address;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "仓库区域编码")
	private String areaCode;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
