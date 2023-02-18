package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.vo.AttrGroupResponseVO;

import java.util.List;
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

    /**
     * 根据分类id查询分类所属属性分组列表
     *
     * @param catelogId 分类id
     * @return
     */
    List<AttrGroupEntity> listByCatelogId(Long catelogId);

    /**
     * 查询分类下的所有属性分组和属性分组下的属性列表
     *
     * @param catelogId 分类id
     * @return
     */
    List<AttrGroupResponseVO> listAttrGroupWithAttr(Long catelogId);
}

