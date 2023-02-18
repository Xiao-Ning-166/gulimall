package com.gulimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.product.dto.SpuQueryDTO;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface SpuInfoMapper extends BaseMapper<SpuInfoEntity> {

    /**
     * 分页查询spu信息
     *
     * @param spuQueryDTO 查询条件
     * @param page        分页信息
     * @return
     */
    IPage<SpuInfoVO> queryPage(@Param("spuQueryDTO") SpuQueryDTO spuQueryDTO, IPage<SpuInfoVO> page);
}
