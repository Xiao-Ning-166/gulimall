package com.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.api.coupon.dto.MemberPriceDTO;
import com.gulimall.coupon.entity.MemberPriceEntity;

import java.util.List;

/**
 * @author xiaoning
 * @description 针对表【sms_member_price(商品会员价格)】的数据库操作Service
 * @createDate 2023-02-04 22:05:59
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    /**
     * 批量保存sku会员价格信息
     *
     * @param memberPrices
     */
    void saveMemberPrices(List<MemberPriceDTO> memberPrices);
}
