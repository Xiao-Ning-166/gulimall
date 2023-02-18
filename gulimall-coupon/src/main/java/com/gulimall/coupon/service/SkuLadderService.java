package com.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.api.coupon.dto.SkuLadderDTO;
import com.gulimall.coupon.entity.SkuLadderEntity;

import java.util.List;

/**
 * @author xiaoning
 * @description 针对表【sms_sku_ladder(商品阶梯价格)】的数据库操作Service
 * @createDate 2023-02-04 22:05:59
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    /**
     * 批量保存sku满减信息
     *
     * @param skuLadders
     */
    void saveSkuLadders(List<SkuLadderDTO> skuLadders);
}
