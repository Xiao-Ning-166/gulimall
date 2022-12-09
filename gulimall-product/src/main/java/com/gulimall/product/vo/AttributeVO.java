package com.gulimall.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 属性对象。接收前端传参
 *
 * @author xiaoning
 * @date 2022/12/04
 */
@Data
public class AttributeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    @NotBlank(message = "请输入属性名")
    private String attrName;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 属性图标
     */
    private String icon;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;
    /**
     * 是否多选【0-单选；1-多选】
     */
    private Integer isMultiple;
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */
    private Integer attrType;
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    private Long enable;
    /**
     * 所属分类id
     */
    @NotNull(message = "请选择所属分类")
    private Long catelogId;
    /**
     * 所属分组id
     */
    // @NotNull(message = "请选择所属分组")
    private List<Long> attrGroupIds;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;
}
