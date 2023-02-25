package com.gulimall.storage.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购项完成对象
 *
 * @author xiaoning
 * @date 2023/02/23
 */
@Data
@ApiModel(value = "采购项完成对象")
public class PurchaseItemFinishDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购项id
     */
    private Long itemId;

    /**
     * 采购数量
     */
    private Integer number;

    /**
     * 备注
     */
    private String remark;

}
