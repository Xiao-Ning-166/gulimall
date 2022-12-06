package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.vo.AttributeResponseVO;
import com.gulimall.product.vo.AttributeVO;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询属性列表
     *
     * @param attrEntity
     * @param page
     * @return
     */
    IPage<AttributeResponseVO> listPage(AttrEntity attrEntity, IPage<AttrEntity> page);

    /**
     * 保存属性
     *
     * @param attr
     */
    void saveAttribute(AttributeVO attr);

    /**
     * 修改属性信息
     *
     * @param attr
     */
    void updateAttributeById(AttributeVO attr);

    /**
     * 删除属性信息
     *
     * @param ids
     */
    void removeAttributeByIds(List<Long> ids);
}

