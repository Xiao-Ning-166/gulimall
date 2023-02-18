package com.gulimall.coupon.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.coupon.dto.MemberPriceDTO;
import com.gulimall.coupon.entity.MemberPriceEntity;
import com.gulimall.coupon.mapper.MemberPriceMapper;
import com.gulimall.coupon.service.MemberPriceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoning
 * @description 针对表【sms_member_price(商品会员价格)】的数据库操作Service实现
 * @createDate 2023-02-04 22:05:59
 */
@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPriceEntity>
        implements MemberPriceService {

    /**
     * 批量保存sku会员价格信息
     *
     * @param memberPrices
     */
    @Override
    public void saveMemberPrices(List<MemberPriceDTO> memberPrices) {
        if (CollectionUtil.isEmpty(memberPrices)) {
            return;
        }
        List<MemberPriceEntity> memberPriceList = memberPrices.stream().map((memberPrice) -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            BeanUtils.copyProperties(memberPrice, memberPriceEntity);
            return memberPriceEntity;
        }).collect(Collectors.toList());
        this.saveBatch(memberPriceList);
    }
}




