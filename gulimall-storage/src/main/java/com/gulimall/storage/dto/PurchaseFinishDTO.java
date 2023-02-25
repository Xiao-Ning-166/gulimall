package com.gulimall.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 采购完成对象
 *
 * @author xiaoning
 * @date 2023/02/23
 */
@Data
@ApiModel(value = "采购完成对象")
public class PurchaseFinishDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单id
     */
    @NotNull(message = "采购单编号不能为空")
    @ApiModelProperty(value = "采购单id")
    private Long orderId;

    /**
     * 采购项集合
     */
    @NotNull(message = "采购项不能为空")
    @ApiModelProperty(value = "采购项集合")
    private List<PurchaseItemFinishDTO> items;

}
