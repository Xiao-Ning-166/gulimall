package com.gulimall.api.storage.model.dto;

import lombok.Data;

/**
 * @author xiaoning
 * @date 2023/03/16
 */
@Data
public class SkuStorageDTO {

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

}
