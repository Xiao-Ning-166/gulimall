package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.dto.SpuQueryDTO;
import com.gulimall.product.dto.SpuSaveDTO;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuInfoVO;

/**
 * spu信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {


    /**
     * 发布商品
     *
     * @param spuSaveDTO 商品信息
     */
    void publishProduct(SpuSaveDTO spuSaveDTO);

    /**
     * 分页查询spu信息
     *
     * @param spuQueryDTO 查询条件
     * @param page        分页信息
     * @return
     */
    IPage<SpuInfoVO> queryPage(SpuQueryDTO spuQueryDTO, IPage<SpuInfoVO> page);
}

