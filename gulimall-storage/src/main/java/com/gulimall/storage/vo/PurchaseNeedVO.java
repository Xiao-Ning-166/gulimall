package com.gulimall.storage.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoning
 * @date 2023/02/21
 */
@Data
@ApiModel(value = "采购需求对象")
public class PurchaseNeedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 采购单id
     */
    private String purchaseId;
    /**
     * 采购商品id
     */
    private String skuId;
    /**
     * 采购数量
     */
    private Integer skuNum;
    /**
     * 采购金额
     */
    private BigDecimal skuPrice;
    /**
     * 仓库id
     */
    private String wareId;
    /**
     * 仓库名称
     */
    private String wareName;
    /**
     * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
     */
    private Integer status;

}
