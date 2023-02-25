package com.gulimall.storage.bo;

import lombok.Data;

/**
 * 库存对象
 *
 * @author xiaoning
 * @date 2023/02/24
 */
@Data
public class StockBO {

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 仓库id
     */
    private Long wareId;

    /**
     * 入库数量
     */
    private Integer realNumber;

}
