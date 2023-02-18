package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.dto.SpuBaseAttrDTO;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.mapper.ProductAttrValueMapper;
import com.gulimall.product.service.ProductAttrValueService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 批量保存spu规格参数信息
     *
     * @param spuBaseAttrs
     * @param spuId
     */
    @Override
    public void saveSpuAttrs(List<SpuBaseAttrDTO> spuBaseAttrs, Long spuId) {
        List<ProductAttrValueEntity> productAttrValueEntities = spuBaseAttrs.stream().map((spuBaseAttrDTO) -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            BeanUtils.copyProperties(spuBaseAttrDTO, productAttrValueEntity);
            productAttrValueEntity.setSpuId(spuId);
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        this.saveBatch(productAttrValueEntities);
    }
}
