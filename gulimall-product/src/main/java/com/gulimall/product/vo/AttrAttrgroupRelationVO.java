package com.gulimall.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "分组关系批量保存对象")
public class AttrAttrgroupRelationVO {

    /**
     * 分组id
     */
    @NotNull
    @ApiModelProperty(value = "分组id")
    private Long attrGroupId;

    /**
     * 属性id
     */
    @NotNull
    @ApiModelProperty(value = "属性id列表")
    private List<Long> attrIds;

}
