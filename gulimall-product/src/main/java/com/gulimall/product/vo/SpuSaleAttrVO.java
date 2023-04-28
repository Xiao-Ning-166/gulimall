package com.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaoning
 * @date 2023/04/24
 */
@Data
public class SpuSaleAttrVO {

    private String attrId;

    private String attrName;

    private List<AttrValueWithSkuIdsVO> attrValues;

}
