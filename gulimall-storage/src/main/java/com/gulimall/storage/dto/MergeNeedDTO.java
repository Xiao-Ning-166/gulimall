package com.gulimall.storage.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/02/22
 */
@Data
@ApiModel(value = "合并采购需求")
public class MergeNeedDTO {

    /**
     * 采购单id
     */
    @NotNull
    private Long orderId;

    /**
     * 采购需求id集合
     */
    @NotNull
    private List<String> needIds;

}
