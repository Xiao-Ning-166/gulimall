package com.gulimall.storage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Data
@TableName("wms_purchase")
public class PurchaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 采购单编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "采购单编号")
	private Long id;
	/**
	 * 采购单名称
	 */
	@ApiModelProperty(value = "采购单名称")
	private String name;
	/**
	 * 采购人id
	 */
	@ApiModelProperty(value = "采购人id")
	private String assigneeId;
	/**
	 * 采购人姓名
	 */
	@ApiModelProperty(value = "采购人姓名")
	private String assigneeName;
	/**
	 * 采购人手机号
	 */
	@ApiModelProperty(value = "采购人手机号")
	private String phone;
	/**
	 * 优先级
	 */
	@ApiModelProperty(value = "优先级")
	private Integer priority;
	/**
	 * 状态【0-新建 1-已分配 2-已领取 3-已完成 4-有异常】
	 */
	private Integer status;
	/**
	 * 仓库id
	 */
	@ApiModelProperty(value = "仓库id")
	private Long wareId;
	/**
	 * 总金额
	 */
	private BigDecimal amount;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
