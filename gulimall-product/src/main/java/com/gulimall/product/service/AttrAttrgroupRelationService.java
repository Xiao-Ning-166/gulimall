package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.vo.AttrAttrgroupRelationVO;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据属性id查询所属分组列表
     *
     * @param attrId
     * @return
     */
    List<AttrGroupEntity> getAttrGroupByAttributeId(Long attrId);

    /**
     * 根据属性分组id获取属性列表
     *
     * @param page
     * @param attrGroupId 属性id
     * @return
     */
    IPage<AttrEntity> getAttrsByAttributeId(IPage page, Long attrGroupId);

    /**
     * 查询当前分类下没有关联属性分组的属性列表
     *
     * @param page
     * @param catelogId 分类id
     * @return
     */
    IPage<AttrEntity> getAttrsNoAttrGroup(IPage page, Long catelogId);

    /**
     * 批量保存关系
     *
     * @param attrAttrgroupRelationVO
     */
    void saveRelation(AttrAttrgroupRelationVO attrAttrgroupRelationVO);

    /**
     * 批量删除属性、属性分组的关联
     *
     * @param attrGroupId 属性所属属性分组id
     * @param attrIds 要删除的属性id集合
     */
    void deleteRelation(Long attrGroupId, List<Long> attrIds);
}

