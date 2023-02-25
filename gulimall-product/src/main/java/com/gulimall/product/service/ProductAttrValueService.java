package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.dto.SpuBaseAttrDTO;
import com.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 批量保存spu规格参数信息
     *
     * @param spuBaseAttrs
     * @param spuId
     */
    void saveSpuAttrs(List<SpuBaseAttrDTO> spuBaseAttrs, Long spuId);

    /**
     * 根据spuiId查询属性列表
     *
     * @param spuId
     * @return
     */
    List<ProductAttrValueEntity> listAttrValuesBySpuId(Long spuId);

    /**
     * 更新spu规格信息
     *
     * @param spuId
     * @param attrValues
     */
    void updateAttrValuesBySpuId(Long spuId, List<SpuBaseAttrDTO> attrValues);
}

