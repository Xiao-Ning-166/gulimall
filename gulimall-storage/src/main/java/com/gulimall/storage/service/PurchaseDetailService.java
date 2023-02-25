package com.gulimall.storage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.storage.dto.MergeNeedDTO;
import com.gulimall.storage.dto.PurchaseNeedDTO;
import com.gulimall.storage.entity.PurchaseDetailEntity;
import com.gulimall.storage.vo.PurchaseNeedVO;

/**
 *
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    /**
     * 分页查询采购需求信息
     *
     * @param page
     * @param purchaseNeedDTO
     * @return
     */
    IPage<PurchaseNeedVO> queryPage(IPage<PurchaseNeedVO> page, PurchaseNeedDTO purchaseNeedDTO);

    /**
     * 合并采购需求
     * @param mergeNeedDTO
     */
    void mergeNeed(MergeNeedDTO mergeNeedDTO);
}

