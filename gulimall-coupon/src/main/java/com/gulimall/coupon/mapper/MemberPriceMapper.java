package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.MemberPriceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoning
 * @description 针对表【sms_member_price(商品会员价格)】的数据库操作Mapper
 * @createDate 2023-02-04 22:05:59
 * @Entity com.gulimall.coupon.entity.MemberPrice
 */
@Mapper
public interface MemberPriceMapper extends BaseMapper<MemberPriceEntity> {

}




