package com.gulimall.coupon.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.coupon.dto.SkuFullReductionDTO;
import com.gulimall.coupon.entity.SkuFullReductionEntity;
import com.gulimall.coupon.mapper.SkuFullReductionMapper;
import com.gulimall.coupon.service.SkuFullReductionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoning
 * @description 针对表【sms_sku_full_reduction(商品满减信息)】的数据库操作Service实现
 * @createDate 2023-02-04 22:05:59
 */
@Service
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReductionEntity>
        implements SkuFullReductionService {

    /**
     * 批量保存sku满减信息
     *
     * @param skuFullReductions
     */
    @Override
    public void saveSkuFullReductions(List<SkuFullReductionDTO> skuFullReductions) {
        if (CollectionUtil.isEmpty(skuFullReductions)) {
            return;
        }
        List<SkuFullReductionEntity> skuFullReductionEntityList = skuFullReductions.stream().map((skuFullReductionDTO) -> {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuFullReductionDTO, skuFullReductionEntity);
            return skuFullReductionEntity;
        }).collect(Collectors.toList());
        this.saveBatch(skuFullReductionEntityList);
    }
}




