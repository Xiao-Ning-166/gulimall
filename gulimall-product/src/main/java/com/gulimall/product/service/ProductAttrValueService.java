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
}

