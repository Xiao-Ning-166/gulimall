package com.gulimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.product.dto.SkuQueryDTO;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.vo.SkuInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * sku信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface SkuInfoMapper extends BaseMapper<SkuInfoEntity> {

    /**
     * 分页查询sku信息
     *
     * @param skuQueryDTO 查询条件
     * @param page        分页参数
     * @return
     */
    IPage<SkuInfoVO> queryPage(@Param("skuQueryDTO") SkuQueryDTO skuQueryDTO, IPage<SkuInfoVO> page);
}
