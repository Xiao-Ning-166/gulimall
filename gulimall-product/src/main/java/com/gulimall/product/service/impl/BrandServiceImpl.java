package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.mapper.BrandMapper;
import com.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    private BrandMapper brandMapper;

    @Autowired
    public BrandServiceImpl(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询
     *
     * @param brandEntity
     * @param page
     * @return
     */
    @Override
    public IPage<BrandEntity> listPage(BrandEntity brandEntity, IPage<BrandEntity> page) {
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>(brandEntity);
        IPage<BrandEntity> brandPage = brandMapper.selectPage(page, queryWrapper);

        return brandPage;
    }

    /**
     * 删除品牌
     *
     * @param ids id集合
     */
    @Override
    public void removeBrandByIds(List<Long> ids) {
        removeByIds(ids);
    }

}
