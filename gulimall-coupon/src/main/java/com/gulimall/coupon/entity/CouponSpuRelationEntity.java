package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券与产品关联
 * @TableName sms_coupon_spu_relation
 */
@Data
@TableName(value ="sms_coupon_spu_relation")
public class CouponSpuRelationEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * spu_name
     */
    private String spuName;

    private static final long serialVersionUID = 1L;
}
