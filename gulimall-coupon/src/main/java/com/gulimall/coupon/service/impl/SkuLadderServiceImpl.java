package com.gulimall.coupon.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.coupon.dto.SkuLadderDTO;
import com.gulimall.coupon.entity.SkuLadderEntity;
import com.gulimall.coupon.mapper.SkuLadderMapper;
import com.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoning
 * @description 针对表【sms_sku_ladder(商品阶梯价格)】的数据库操作Service实现
 * @createDate 2023-02-04 22:05:59
 */
@Service
public class SkuLadderServiceImpl extends ServiceImpl<SkuLadderMapper, SkuLadderEntity>
        implements SkuLadderService {

    /**
     * 批量保存sku满减信息
     *
     * @param skuLadders
     */
    @Override
    public void saveSkuLadders(List<SkuLadderDTO> skuLadders) {
        if (CollectionUtil.isEmpty(skuLadders)) {
            return;
        }
        List<SkuLadderEntity> skuLadderEntityList = skuLadders.stream().map((skuLadderDTO) -> {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            BeanUtils.copyProperties(skuLadderDTO, skuLadderEntity);
            return skuLadderEntity;
        }).collect(Collectors.toList());
        this.saveBatch(skuLadderEntityList);
    }
}




