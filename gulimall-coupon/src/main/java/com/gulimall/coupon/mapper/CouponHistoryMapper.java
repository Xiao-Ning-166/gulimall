package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.CouponHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoning
* @description 针对表【sms_coupon_history(优惠券领取历史记录)】的数据库操作Mapper
* @createDate 2023-02-04 22:05:58
* @Entity com.gulimall.coupon.entity.CouponHistory
*/
@Mapper
public interface CouponHistoryMapper extends BaseMapper<CouponHistoryEntity> {

}




