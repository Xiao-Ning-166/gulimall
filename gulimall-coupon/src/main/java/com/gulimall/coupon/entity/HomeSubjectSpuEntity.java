package com.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 专题商品
 * @TableName sms_home_subject_spu
 */
@Data
@TableName(value ="sms_home_subject_spu")
public class HomeSubjectSpuEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 专题名字
     */
    private String name;

    /**
     * 专题id
     */
    private Long subjectId;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * 排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}
