package com.gulimall.storage.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author xiaoning
 * @date 2023/02/21
 */
@Data
@ApiModel(value = "采购需求查询对象")
public class PurchaseNeedDTO {

    /**
     * 仓库id
     */
    private String storageId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 查询关键词
     */
    private String keyword;

}
