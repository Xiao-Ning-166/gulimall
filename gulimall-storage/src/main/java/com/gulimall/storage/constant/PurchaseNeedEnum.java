package com.gulimall.storage.constant;

/**
 * 采购需求状态常量
 *
 * @author xiaoning
 * @date 2023/02/23
 */
public enum PurchaseNeedEnum {
    /**
     * 新建
     */
    CREATED(0),
    /**
     * 已分配
     */
    ASSIGNED(1),
    /**
     * 采购中
     */
    PURCHASING(2),
    /**
     * 已完成
     */
    FINISHED(3),
    /**
     * 采购失败
     */
    FAILED(4);

    private Integer status;

    PurchaseNeedEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
