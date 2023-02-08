package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.CouponSpuCategoryRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoning
* @description 针对表【sms_coupon_spu_category_relation(优惠券分类关联)】的数据库操作Mapper
* @createDate 2023-02-04 22:05:58
* @Entity com.gulimall.coupon.entity.CouponSpuCategoryRelation
*/
@Mapper
public interface CouponSpuCategoryRelationMapper extends BaseMapper<CouponSpuCategoryRelationEntity> {

}




