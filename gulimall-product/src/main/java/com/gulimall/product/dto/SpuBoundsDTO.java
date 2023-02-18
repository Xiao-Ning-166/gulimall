package com.gulimall.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaoning
 * @date 2023/02/04
 */
@Data
@ApiModel("spu积分对象")
public class SpuBoundsDTO {

    /**
     * 金币
     */
    private BigDecimal buyBounds;

    /**
     * 成长值
     */
    private BigDecimal growBounds;

}
