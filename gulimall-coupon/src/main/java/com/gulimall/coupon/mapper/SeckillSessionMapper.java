package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.SeckillSessionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoning
 * @description 针对表【sms_seckill_session(秒杀活动场次)】的数据库操作Mapper
 * @createDate 2023-02-04 22:05:59
 * @Entity com.gulimall.coupon.entity.SeckillSession
 */
@Mapper
public interface SeckillSessionMapper extends BaseMapper<SeckillSessionEntity> {

}




