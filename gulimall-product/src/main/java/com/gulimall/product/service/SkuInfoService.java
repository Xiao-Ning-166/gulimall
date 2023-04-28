package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.product.dto.SkuQueryDTO;
import com.gulimall.product.dto.SpuSkuDTO;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SkuInfoVO;
import com.gulimall.product.vo.SkuItemVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    /**
     * 批量保存sku信息
     *
     * @param skus          sku集合
     * @param spuInfoEntity spu信息
     */
    void saveSkus(List<SpuSkuDTO> skus, SpuInfoEntity spuInfoEntity);

    /**
     * 分页查询sku信息
     *
     * @param skuQueryDTO 查询条件
     * @param page        分页参数
     * @return
     */
    IPage<SkuInfoVO> queryPage(SkuQueryDTO skuQueryDTO, IPage<SkuInfoVO> page);

    /**
     * 查询sku集合
     *
     * @param spuId
     * @return
     */
    List<ProductPutawayEsDTO> listSkusBySpuId(String spuId);

    /**
     * 查询sku详细信息
     *
     * @param skuId
     * @return
     */
    SkuItemVO getSkuDetailBySkuId(Long skuId) throws ExecutionException, InterruptedException;
}

