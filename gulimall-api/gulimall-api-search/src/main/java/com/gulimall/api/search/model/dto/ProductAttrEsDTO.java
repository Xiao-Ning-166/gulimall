package com.gulimall.api.search.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品上架属性对象
 *
 * @author xiaoning
 * @date 2023/03/13
 */
@Data
public class ProductAttrEsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private String attrId;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 属性值
     */
    private String attrValue;
}
