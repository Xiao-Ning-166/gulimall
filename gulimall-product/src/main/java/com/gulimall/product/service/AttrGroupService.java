package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param attrGroupEntity 查询参数
     * @param page            分页参数
     * @return
     */
    IPage<AttrGroupEntity> listPage(AttrGroupEntity attrGroupEntity, IPage<AttrGroupEntity> page);
}

