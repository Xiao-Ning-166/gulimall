package com.gulimall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.mapper.AttrAttrgroupRelationMapper;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.vo.AttrAttrgroupRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据属性id查询所属分组名列表
     *
     * @param attrId
     * @return
     */
    @Override
    public List<AttrGroupEntity> getAttrGroupByAttributeId(Long attrId) {
        return attrAttrgroupRelationMapper.getAttrGroupByAttributeId(attrId);
    }

    /**
     * 根据属性分组id获取属性列表
     *
     * @param page
     * @param attrGroupId 属性id
     * @return
     */
    @Override
    public IPage<AttrEntity> getAttrsByAttributeId(IPage page, Long attrGroupId) {
        return attrAttrgroupRelationMapper.getAttrsByAttributeId(page, attrGroupId);
    }

    /**
     * 查询当前分类下没有关联属性分组的属性列表
     *
     * @param page
     * @param catelogId 分类id
     * @return
     */
    @Override
    public IPage<AttrEntity> getAttrsNoAttrGroup(IPage page, Long catelogId) {
        return attrAttrgroupRelationMapper.getAttrsNoAttrGroup(page, catelogId);
    }

    /**
     * 批量保存关系
     *
     * @param attrAttrgroupRelationVO
     */
    @Override
    public void saveRelation(AttrAttrgroupRelationVO attrAttrgroupRelationVO) {
        Long attrGroupId = attrAttrgroupRelationVO.getAttrGroupId();
        List<Long> attrIds = attrAttrgroupRelationVO.getAttrIds();
        if (CollectionUtil.isEmpty(attrIds)) {
            throw new RuntimeException("请选择属性后，再进行关联操作!");
        }
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = new ArrayList<>();
        for (Long attrId : attrIds) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrId);
            attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);

            attrAttrgroupRelationList.add(attrAttrgroupRelationEntity);
        }
        // 批量保存
        saveBatch(attrAttrgroupRelationList);
    }

    /**
     * 批量删除属性、属性分组的关联
     *
     * @param attrGroupId 属性所属的属性分组id
     * @param attrIds 要删除的属性id集合
     */
    @Override
    public void deleteRelation(Long attrGroupId, List<Long> attrIds) {
        if (CollectionUtil.isEmpty(attrIds)) {
            throw new RuntimeException("请先选择属性后，再进行批量删除操作!");
        }
        attrAttrgroupRelationMapper.deleteRelation(attrGroupId, attrIds);
    }

}
