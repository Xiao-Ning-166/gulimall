package com.gulimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.ProductAttrValueEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * spu属性值
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface ProductAttrValueMapper extends BaseMapper<ProductAttrValueEntity> {

    /**
     * 根据spuId查询属性列表
     *
     * @param spuId
     * @return
     */
    List<ProductAttrValueEntity> listAttrValuesBySpuId(Long spuId);
}
