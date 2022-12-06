package com.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 属性响应对象
 *
 * @author xiaoning
 * @date 2022/12/04
 */
@Data
public class AttributeResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
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
    private Long catelogId;
    /**
     * 所属分组id
     */
    private List<Long> attrGroupIds;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分组名称
     */
    private String attrGroupName;

}
