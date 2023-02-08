package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoning
* @description 针对表【sms_coupon(优惠券信息)】的数据库操作Mapper
* @createDate 2023-02-04 22:05:58
* @Entity com.gulimall.coupon.entity.Coupon
*/
@Mapper
public interface CouponMapper extends BaseMapper<CouponEntity> {

}




