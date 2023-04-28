package com.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaoning
 * @date 2023/04/22
 */
@Data
public class SpuBaseAttrVO {

    /**
     * 属性分组名
     */
    private String groupName;

    private List<AttrVO> attr;


}
