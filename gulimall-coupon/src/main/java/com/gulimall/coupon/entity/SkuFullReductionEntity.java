package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品满减信息
 * @TableName sms_sku_full_reduction
 */
@Data
@TableName(value ="sms_sku_full_reduction")
public class SkuFullReductionEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * spu_id
     */
    private Long skuId;

    /**
     * 满多少
     */
    private BigDecimal fullPrice;

    /**
     * 减多少
     */
    private BigDecimal reducePrice;

    /**
     * 是否参与其他优惠
     */
    private Integer addOther;

    private static final long serialVersionUID = 1L;
}
