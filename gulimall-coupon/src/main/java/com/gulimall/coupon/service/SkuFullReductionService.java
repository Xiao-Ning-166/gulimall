package com.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.api.coupon.dto.SkuFullReductionDTO;
import com.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.List;

/**
 * @author xiaoning
 * @description 针对表【sms_sku_full_reduction(商品满减信息)】的数据库操作Service
 * @createDate 2023-02-04 22:05:59
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    /**
     * 批量保存sku满减信息
     *
     * @param skuFullReductions
     */
    void saveSkuFullReductions(List<SkuFullReductionDTO> skuFullReductions);
}
