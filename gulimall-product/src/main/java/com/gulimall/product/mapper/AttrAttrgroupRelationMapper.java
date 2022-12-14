package com.gulimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 根据属性id查询关联分组列表
     *
     * @param attrId
     * @return
     */
    List<AttrGroupEntity> getAttrGroupByAttributeId(Long attrId);

    /**
     * 根据属性分组id查询分组关联的属性列表
     *
     * @param page
     * @param attrGroupId
     * @return
     */
    IPage<AttrEntity> getAttrsByAttributeId(IPage page, @Param("attrGroupId") Long attrGroupId);

    /**
     * 查询当前分类下没有关联属性分组的属性列表
     *
     * @param page
     * @param catelogId
     * @return
     */
    IPage<AttrEntity> getAttrsNoAttrGroup(IPage page, @Param("catelogId") Long catelogId);

    /**
     * 批量删除属性、属性分组的关联
     *
     * @param attrGroupId 属性所属的属性分组id
     * @param attrIds     要删除的属性id集合
     */
    void deleteRelation(@Param("attrGroupId") Long attrGroupId, @Param("attrIds") List<Long> attrIds);
}
