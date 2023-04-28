package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.mapper.SkuSaleAttrValueMapper;
import com.gulimall.product.service.SkuSaleAttrValueService;
import com.gulimall.product.vo.SpuSaleAttrVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据spuId查询
     *
     * @param spuId
     * @return
     */
    @Override
    public List<SpuSaleAttrVO> listSaleAttrsBySpuId(Long spuId) {
        return this.baseMapper.listSaleAttrsBySpuId(spuId);
    }
}
