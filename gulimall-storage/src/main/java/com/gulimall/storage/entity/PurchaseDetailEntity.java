package com.gulimall.storage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
@Data
@TableName("wms_purchase_detail")
public class PurchaseDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 采购单id
	 */
	@ApiModelProperty(value = "采购单id")
	private Long purchaseId;
	/**
	 * 采购商品id
	 */
	@ApiModelProperty(value = "采购商品id")
	private Long skuId;
	/**
	 * 应采购数量
	 */
	@ApiModelProperty(value = "应采购数量")
	private Integer skuNum;
	/**
	 * 实际采购数量
	 */
	@ApiModelProperty(value = "实际采购数量")
	private Integer realNumber;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 采购金额
	 */
	@ApiModelProperty(value = "采购金额")
	private BigDecimal skuPrice;
	/**
	 * 仓库id
	 */
	@ApiModelProperty(value = "仓库id")
	private Long wareId;
	/**
	 * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
	 */
	@ApiModelProperty(value = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
