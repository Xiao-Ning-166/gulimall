package com.gulimall.order.mapper;

import com.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 20:04:43
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

}
