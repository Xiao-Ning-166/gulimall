package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀活动商品关联
 * @TableName sms_seckill_sku_relation
 */
@Data
@TableName(value ="sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 活动id
     */
    private Long promotionId;

    /**
     * 活动场次id
     */
    private Long promotionSessionId;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 秒杀价格
     */
    private Integer seckillPrice;

    /**
     * 秒杀总量
     */
    private Integer seckillCount;

    /**
     * 每人限购数量
     */
    private Integer seckillLimit;

    /**
     * 排序
     */
    private Integer seckillSort;

    private static final long serialVersionUID = 1L;
}