package com.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 属性分组响应对象（带属性信息）
 *
 * @author xiaoning
 * @date 2022/12/31
 */
@Data
@ApiModel(value = "属性分组响应对象")
public class AttrGroupResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long attrGroupId;

    /**
     * 组名
     */
    @ApiModelProperty(value = "属性分组名称")
    private String attrGroupName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 描述
     */
    private String descript;

    /**
     * 组图标
     */
    private String icon;

    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     * 属性列表
     */
    private List<AttributeResponseVO> attrs;

}
