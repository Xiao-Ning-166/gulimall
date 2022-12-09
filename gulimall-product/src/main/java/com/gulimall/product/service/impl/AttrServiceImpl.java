package com.gulimall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.mapper.AttrMapper;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.AttributeResponseVO;
import com.gulimall.product.vo.AttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询属性列表
     *
     * @param attrEntity
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<AttributeResponseVO> listPage(AttrEntity attrEntity, IPage<AttrEntity> page) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>(attrEntity);
        IPage<AttrEntity> attrPage = page(page, queryWrapper);
        List<AttrEntity> attrRecords = attrPage.getRecords();
        List<AttributeResponseVO> attributeResponseVOList = attrRecords.stream().map((attr) -> {
            AttributeResponseVO attributeResponseVO = new AttributeResponseVO();
            BeanUtil.copyProperties(attr, attributeResponseVO, false);
            // 查询所属分类名
            CategoryEntity category = categoryService.getById(attr.getCatelogId());
            attributeResponseVO.setCategoryName(category.getName());
            // 查询所属分组名
            List<AttrGroupEntity> attrGroups = attrAttrgroupRelationService.getAttrGroupByAttributeId(attr.getAttrId());
            List<String> attrGroupNames = new ArrayList<>(attrGroups.size());
            List<Long> attrGroupIds = new ArrayList<>(attrGroups.size());
            if (CollectionUtil.isNotEmpty(attrGroups)) {
                attrGroups.stream().forEach((attrGroup) -> {
                   attrGroupNames.add(attrGroup.getAttrGroupName());
                   attrGroupIds.add(attrGroup.getAttrGroupId());
                });
            }
            String attrGroupNamesStr = CollectionUtil.join(attrGroupNames, ",");
            attributeResponseVO.setAttrGroupName(attrGroupNamesStr);
            // String attrGroupIdsStr = CollectionUtil.join(attrGroupIds, ",");
            attributeResponseVO.setAttrGroupIds(attrGroupIds);
            return attributeResponseVO;
        }).collect(Collectors.toList());
        IPage<AttributeResponseVO> attributeResponseVOPage = new Page<>();
        BeanUtil.copyProperties(attrPage, attributeResponseVOPage, false);
        attributeResponseVOPage.setRecords(attributeResponseVOList);
        return attributeResponseVOPage;
    }

    /**
     * 保存属性
     *
     * @param attr
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttribute(AttributeVO attr) {
        // 1、保存属性基本数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attr, attrEntity, false);
        save(attrEntity);

        // 2、保存属性、属性组关联关系
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = new ArrayList<>();
        List<Long> attrGroupIds = attr.getAttrGroupIds();
        if (CollectionUtil.isEmpty(attrGroupIds)) {
            return;
        }
        for (Long attrGroupId : attrGroupIds) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());

            attrAttrgroupRelationList.add(attrAttrgroupRelationEntity);
        }
        attrAttrgroupRelationService.saveBatch(attrAttrgroupRelationList);
    }

    /**
     * 修改属性信息
     *
     * @param attr
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttributeById(AttributeVO attr) {
        // 1、修改基本属性
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attr, attrEntity, false);
        updateById(attrEntity);

        // 2、修改属性和属性分组关联信息
        // 2.1、删除旧的关联信息
        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_id", attrEntity.getAttrId());
        attrAttrgroupRelationService.remove(queryWrapper);
        // 2.2、重新建立关联
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = new ArrayList<>();
        List<Long> attrGroupIds = attr.getAttrGroupIds();
        for (Long attrGroupId : attrGroupIds) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);

            attrAttrgroupRelationList.add(attrAttrgroupRelationEntity);
        }
        // 2.3、批量保存
        attrAttrgroupRelationService.saveBatch(attrAttrgroupRelationList);
    }

    /**
     * 删除属性信息
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAttributeByIds(List<Long> ids) {
        // 1、删除属性基本信息
        removeByIds(ids);

        // 2、删除属性和属性分组的关联关系
        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("attr_id", ids);
        attrAttrgroupRelationService.remove(queryWrapper);
    }

}
