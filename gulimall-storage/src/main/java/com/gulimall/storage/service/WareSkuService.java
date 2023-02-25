package com.gulimall.storage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.storage.bo.StockBO;
import com.gulimall.storage.entity.WareSkuEntity;
import com.gulimall.storage.vo.WareSkuVO;

import java.util.List;

/**
 * 商品库存
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 21:41:55
 */
public interface WareSkuService extends IService<WareSkuEntity> {


    /**
     * 分页查询商品库存列表
     *
     * @param page
     * @param storageId 仓库id
     * @param skuId     sku id
     * @return
     */
    IPage<WareSkuVO> queryPage(IPage<WareSkuVO> page, String storageId, String skuId);

    /**
     * 添加库存
     *
     * @param stockBOs
     */
    void addStock(List<StockBO> stockBOs);
}

