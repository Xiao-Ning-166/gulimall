package com.gulimall.storage.constant;

/**
 * 采购单状态枚举
 *
 * @author xiaoning
 * @date 2023/02/23
 */
public enum PurchaseOrderEnum {
    /**
     * 新建
     */
    CREATED(0),
    /**
     * 已分配
     */
    ASSIGNED(1),
    /**
     * 已领取
     */
    RECEIVED(2),
    /**
     * 已完成
     */
    FINISHED(3),
    /**
     * 有异常的
     */
    FAILED(4);

    private Integer status;

    PurchaseOrderEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
