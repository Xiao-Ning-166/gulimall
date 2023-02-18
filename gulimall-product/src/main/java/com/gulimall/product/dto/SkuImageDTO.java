package com.gulimall.product.dto;

import lombok.Data;

/**
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
public class SkuImageDTO {

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 是否是默认图片。0-不是，1-是
     */
    private Integer defaultImg;

}
