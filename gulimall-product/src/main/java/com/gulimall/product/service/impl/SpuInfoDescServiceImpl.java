package com.gulimall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.mapper.SpuInfoDescMapper;
import com.gulimall.product.service.SpuInfoDescService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescMapper, SpuInfoDescEntity> implements SpuInfoDescService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoDescEntity> page = this.page(
                new Query<SpuInfoDescEntity>().getPage(params),
                new QueryWrapper<SpuInfoDescEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 批量保存spu描述图片信息
     *
     * @param descriptionImageUrls 描述图片地址集合
     * @param spuId
     */
    @Override
    public void saveDescriptionImagesBatch(List<String> descriptionImageUrls, Long spuId) {
        // 判断是否上传了描述图片
        if (CollectionUtil.isEmpty(descriptionImageUrls)) {
            // 未上传，直接返回
            return;
        }

        List<SpuInfoDescEntity> spuImageList = descriptionImageUrls.stream().map((descriptionImageUrl) -> {
            SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
            spuInfoDescEntity.setSpuId(spuId);
            spuInfoDescEntity.setDecript(descriptionImageUrl);

            return spuInfoDescEntity;
        }).collect(Collectors.toList());

        // 保存spu图片信息
        this.saveBatch(spuImageList);
    }

}
