package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.SeckillPromotionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoning
 * @description 针对表【sms_seckill_promotion(秒杀活动)】的数据库操作Mapper
 * @createDate 2023-02-04 22:05:59
 * @Entity com.gulimall.coupon.entity.SeckillPromotion
 */
@Mapper
public interface SeckillPromotionMapper extends BaseMapper<SeckillPromotionEntity> {

}



