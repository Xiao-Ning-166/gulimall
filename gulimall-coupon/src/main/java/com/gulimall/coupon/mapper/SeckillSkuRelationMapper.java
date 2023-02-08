package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.SeckillSkuRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoning
 * @description 针对表【sms_seckill_sku_relation(秒杀活动商品关联)】的数据库操作Mapper
 * @createDate 2023-02-04 22:05:59
 * @Entity com.gulimall.coupon.entity.SeckillSkuRelation
 */
@Mapper
public interface SeckillSkuRelationMapper extends BaseMapper<SeckillSkuRelationEntity> {

}




