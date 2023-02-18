package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Sku属性对象
 *
 * @author xiaoning
 * @date 2023/02/04
 */
@ApiModel("sku属性对象")
@Data
public class SkuAttrDTO {

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 属性值
     */
    private String attrValue;

}
