package com.gulimall.storage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author xiaoning
 * @date 2023/02/19
 */
@Data
public class WareInfoDTO {

    /**
     * 仓库名
     */
    @NotEmpty(message = "仓库名称不能为空")
    @ApiModelProperty(value = "仓库名")
    private String name;

    /**
     * 仓库地址
     */
    @ApiModelProperty(value = "仓库地址")
    private String address;

    /**
     * 区域编码
     */
    @NotEmpty(message = "仓库区域编码不能为空")
    @ApiModelProperty(value = "仓库区域编码")
    private String areaCode;

}
