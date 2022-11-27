package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param brandEntity
     * @param page
     * @return
     */
    IPage<BrandEntity> listPage(BrandEntity brandEntity, IPage<BrandEntity> page);

    /**
     * 删除品牌
     *
     * @param ids id集合
     */
    void removeBrandByIds(List<Long> ids);
}

