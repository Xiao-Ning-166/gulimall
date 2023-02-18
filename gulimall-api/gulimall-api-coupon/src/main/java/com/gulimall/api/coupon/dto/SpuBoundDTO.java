package com.gulimall.api.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * spu 积分信息
 *
 * @author xiaoning
 * @date 2023/02/06
 */
@Data
@ApiModel("spu积分信息对象")
public class SpuBoundDTO {

    /**
     * spu id
     */
    @NotNull(message = "spu id不能为空")
    @ApiModelProperty(value = "spu id", required = true)
    private Long spuId;

    /**
     * 金币积分
     */
    @ApiModelProperty(value = "金币积分")
    private BigDecimal buyBounds;

    /**
     * 成长积分
     */
    @ApiModelProperty(value = "成长积分")
    private BigDecimal growBounds;

}
