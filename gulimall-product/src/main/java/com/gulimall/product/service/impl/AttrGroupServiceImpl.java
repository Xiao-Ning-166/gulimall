package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.mapper.AttrGroupMapper;
import com.gulimall.product.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
