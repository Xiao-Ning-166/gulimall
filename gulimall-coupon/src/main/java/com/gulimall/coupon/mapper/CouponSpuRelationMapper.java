package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.CouponSpuRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoning
* @description 针对表【sms_coupon_spu_relation(优惠券与产品关联)】的数据库操作Mapper
* @createDate 2023-02-04 22:05:58
* @Entity com.gulimall.coupon.entity.CouponSpuRelation
*/
@Mapper
public interface CouponSpuRelationMapper extends BaseMapper<CouponSpuRelationEntity> {

}




