package com.gulimall.storage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单对象
 *
 * @author xiaoning
 * @date 2023/02/22
 */
@Data
@ApiModel(value = "采购单对象")
public class PurchaseOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单编号
     */
    @ApiModelProperty(value = "采购单编号")
    private String id;
    /**
     * 采购单名称
     */
    @ApiModelProperty(value = "采购单名称")
    private String name;
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
    private String wareId;
    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String wareName;
    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
