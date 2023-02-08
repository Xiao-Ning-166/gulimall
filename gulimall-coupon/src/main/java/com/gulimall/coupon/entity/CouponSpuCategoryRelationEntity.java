package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券分类关联
 * @TableName sms_coupon_spu_category_relation
 */
@Data
@TableName(value ="sms_coupon_spu_category_relation")
public class CouponSpuCategoryRelationEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 产品分类id
     */
    private Long categoryId;

    /**
     * 产品分类名称
     */
    private String categoryName;

    private static final long serialVersionUID = 1L;
}
