package com.gulimall.search.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaoning
 * @date 2023/04/08
 */
@Data
public class AttrVO {

    private String attrId;

    private String attrName;

    private List<String> attrValues;

}
