package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.mapper.AttrGroupMapper;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.vo.AttrGroupResponseVO;
import com.gulimall.product.vo.SpuBaseAttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrGroupMapper attrGroupMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询
     *
     * @param attrGroupEntity 查询参数
     * @param page            分页参数
     * @return
     */
    @Override
    public IPage<AttrGroupEntity> listPage(AttrGroupEntity attrGroupEntity, IPage<AttrGroupEntity> page) {
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>(attrGroupEntity);
        IPage<AttrGroupEntity> attrGroupPage = attrGroupMapper.selectPage(page, queryWrapper);
        return attrGroupPage;
    }

    /**
     * 根据分类id查询分类所属属性分组列表
     *
     * @param catelogId 分类id
     * @return
     */
    @Override
    public List<AttrGroupEntity> listByCatelogId(Long catelogId) {
        List<AttrGroupEntity> attrGroupList = query().eq("catelog_id", catelogId).list();
        return attrGroupList;
    }

    /**
     * 查询分类下的所有属性分组和属性分组下的属性列表
     *
     * @param catelogId 分类id
     * @return
     */
    @Override
    public List<AttrGroupResponseVO> listAttrGroupWithAttr(Long catelogId) {
        return attrGroupMapper.listAttrGroupWithAttr(catelogId);
    }

    /**
     * 通过spuId、分类id查询规格参数信息
     *
     * @param spuId
     * @param catalogId
     * @return
     */
    @Override
    public List<SpuBaseAttrVO> listAttrGroupWithAttrsBySpuIdAndCatalogId(Long spuId, Long catalogId) {
        return this.baseMapper.listAttrGroupWithAttrsBySpuIdAndCatalogId(spuId, catalogId);
    }
}
