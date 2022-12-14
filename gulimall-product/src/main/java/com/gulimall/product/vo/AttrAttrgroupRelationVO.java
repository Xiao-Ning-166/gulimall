package com.gulimall.product.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分组关系批量保存
 *
 * @author xiaoning
 * @date 2022/12/11
 */
@Data
public class AttrAttrgroupRelationVO {

    /**
     * 分组id
     */
    @NotNull
    private Long attrGroupId;

    /**
     * 属性id
     */
    @NotNull
    private List<Long> attrIds;

}
