package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀活动
 * @TableName sms_seckill_promotion
 */
@Data
@TableName(value ="sms_seckill_promotion")
public class SeckillPromotionEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 开始日期
     */
    private Date startTime;

    /**
     * 结束日期
     */
    private Date endTime;

    /**
     * 上下线状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
