package com.gulimall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.coupon.dto.MemberPriceDTO;
import com.gulimall.api.coupon.dto.SkuFullReductionDTO;
import com.gulimall.api.coupon.dto.SkuLadderDTO;
import com.gulimall.api.coupon.feign.SkuFullReductionFeignClient;
import com.gulimall.api.coupon.feign.SkuLadderFeignClient;
import com.gulimall.api.coupon.feign.SkuMemberPriceFeignClient;
import com.gulimall.product.constant.SkuConstants;
import com.gulimall.product.dto.SkuAttrDTO;
import com.gulimall.product.dto.SkuImageDTO;
import com.gulimall.product.dto.SkuMemberPriceDTO;
import com.gulimall.product.dto.SkuQueryDTO;
import com.gulimall.product.dto.SpuSkuDTO;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.mapper.SkuInfoMapper;
import com.gulimall.product.service.SkuImagesService;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.service.SkuSaleAttrValueService;
import com.gulimall.product.vo.SkuInfoVO;
import com.gulimall.product.vo.SpuInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private SkuFullReductionFeignClient skuFullReductionFeignClient;

    @Autowired
    private SkuLadderFeignClient skuLadderFeignClient;

    @Autowired
    private SkuMemberPriceFeignClient skuMemberPriceFeignClient;

    /**
     * 批量保存sku信息
     *
     * @param skus          sku集合
     * @param spuInfoEntity spu信息
     */
    @Override
    public void saveSkus(List<SpuSkuDTO> skus, SpuInfoEntity spuInfoEntity) {
        if (CollectionUtil.isEmpty(skus)) {
            return;
        }
        List<SkuInfoEntity> skuInfos = new ArrayList<>();
        List<SkuImagesEntity> skuImages = new ArrayList<>();
        List<SkuSaleAttrValueEntity> skuSaleAttrValues = new ArrayList<>();
        List<SkuFullReductionDTO> skuFullReductions = new ArrayList<>();
        List<SkuLadderDTO> skuLadders = new ArrayList<>();
        List<MemberPriceDTO> memberPrices = new ArrayList<>();
        for (SpuSkuDTO spuSkuDTO : skus) {
            // 3.1、sku基本信息 pms_sku_info
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(spuSkuDTO, skuInfoEntity);
            skuInfoEntity.setSkuId(IdWorker.getId(spuSkuDTO));
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setSaleCount(SkuConstants.DEFAULT_SALE_COUNT);
            skuInfos.add(skuInfoEntity);

            // 3.2、sku图片信息 pms_sku_images
            List<SkuImageDTO> images = spuSkuDTO.getImages();
            if (CollectionUtil.isNotEmpty(images)) {
                images.stream().filter((image) -> {
                    return StrUtil.isNotBlank(image.getImgUrl());
                }).forEach((image) -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    BeanUtils.copyProperties(image, skuImagesEntity);
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());

                    skuImages.add(skuImagesEntity);
                });
            }

            // 3.3、sku属性信息 pms_sku_sale_attr_value
            List<SkuAttrDTO> attrs = spuSkuDTO.getAttr();
            if (CollectionUtil.isNotEmpty(attrs)) {
                attrs.stream().forEach((attr) -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());

                    skuSaleAttrValues.add(skuSaleAttrValueEntity);
                });
            }

            // 3.5、sku满减、优惠信息 gulimall-coupon.sms_sku_full_reduction
            SkuFullReductionDTO skuFullReductionDTO = new SkuFullReductionDTO();
            skuFullReductionDTO.setSkuId(skuInfoEntity.getSkuId());
            skuFullReductionDTO.setFullPrice(spuSkuDTO.getFullPrice());
            skuFullReductionDTO.setReducePrice(spuSkuDTO.getReducePrice());
            skuFullReductions.add(skuFullReductionDTO);

            // 3.6、满几件打几折 gulimall-coupon.sms_sku_ladder
            SkuLadderDTO skuLadderDTO = new SkuLadderDTO();
            skuLadderDTO.setSkuId(skuInfoEntity.getSkuId());
            skuLadderDTO.setFullCount(spuSkuDTO.getFullCount());
            skuLadderDTO.setDiscount(spuSkuDTO.getDiscount());
            skuLadders.add(skuLadderDTO);

            // 3.7、sku会员价格信息 gulimall-coupon.sms_member_price
            List<SkuMemberPriceDTO> memberPriceList = spuSkuDTO.getMemberPrice();
            if (CollectionUtil.isNotEmpty(memberPriceList)) {
                for (SkuMemberPriceDTO skuMemberPriceDTO : memberPriceList) {
                    MemberPriceDTO memberPriceDTO = new MemberPriceDTO();
                    memberPriceDTO.setSkuId(skuInfoEntity.getSkuId());
                    memberPriceDTO.setMemberLevelId(skuMemberPriceDTO.getId());
                    memberPriceDTO.setMemberLevelName(skuMemberPriceDTO.getName());
                    memberPriceDTO.setMemberPrice(skuMemberPriceDTO.getPrice());

                    memberPrices.add(memberPriceDTO);
                }
            }
        }
        this.saveBatch(skuInfos);
        skuImagesService.saveBatch(skuImages);
        skuSaleAttrValueService.saveBatch(skuSaleAttrValues);
        skuFullReductionFeignClient.saveSkuFullReductions(skuFullReductions);
        skuLadderFeignClient.saveSkuLadders(skuLadders);
        skuMemberPriceFeignClient.saveSkuMemberPrices(memberPrices);
    }

    /**
     * 分页查询sku信息
     *
     * @param skuQueryDTO 查询条件
     * @param page        分页参数
     * @return
     */
    @Override
    public IPage<SkuInfoVO> queryPage(SkuQueryDTO skuQueryDTO, IPage<SkuInfoVO> page) {
        return skuInfoMapper.queryPage(skuQueryDTO, page);
    }


}
