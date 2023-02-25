package com.gulimall.storage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.storage.dto.PurchaseFinishDTO;
import com.gulimall.storage.entity.PurchaseDTO;
import com.gulimall.storage.entity.PurchaseEntity;
import com.gulimall.storage.vo.PurchaseOrderVO;
import com.gulimall.storage.vo.PurchaseSelectVO;

import java.util.List;

/**
 * 采购信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    /**
     * 分页查询采购单信息
     *
     * @param page
     * @param purchaseDTO
     * @return
     */
    IPage<PurchaseOrderVO> queryPage(IPage<PurchaseOrderVO> page, PurchaseDTO purchaseDTO);

    /**
     * 查询所有新建/已分配的采购单信息
     *
     * @return
     */
    List<PurchaseSelectVO> listPurchasesSelectVOs();

    /**
     * 采购单id集合
     *
     * @param orderIds
     */
    void receiveOrders(List<Long> orderIds);

    /**
     * 完成采购单
     *
     * @param finishDTO
     */
    void finishOrders(PurchaseFinishDTO finishDTO);
}

