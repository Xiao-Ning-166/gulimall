package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
@ApiModel("spu基础对象")
public class SpuBaseAttrDTO {

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性值
     */
    private String attrValues;

    /**
     * 是否是快速展示
     */
    private Integer showDesc;

}
