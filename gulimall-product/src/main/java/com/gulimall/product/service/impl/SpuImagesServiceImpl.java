package com.gulimall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.product.entity.SpuImagesEntity;
import com.gulimall.product.mapper.SpuImagesMapper;
import com.gulimall.product.service.SpuImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 批量保存spu图片信息
     *
     * @param spuImages spu图片url地址集合
     * @param spuId
     */
    @Override
    public void saveSpuImagesBatch(List<String> spuImages, Long spuId) {
        // 判断是否上传了图片
        if (CollectionUtil.isEmpty(spuImages)) {
            // 未上传，直接返回
            return;
        }

        List<SpuImagesEntity> spuImageList = spuImages.stream().map((spuImageUrl) -> {
            SpuImagesEntity spuImageEntity = new SpuImagesEntity();
            spuImageEntity.setSpuId(spuId);
            spuImageEntity.setImgUrl(spuImageUrl);

            return spuImageEntity;
        }).collect(Collectors.toList());

        // 保存spu图片信息
        this.saveBatch(spuImageList);
    }

}
