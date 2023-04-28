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
import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.api.storage.feign.ProductStorageFeignClient;
import com.gulimall.api.storage.model.dto.SkuStorageDTO;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.constant.SkuConstants;
import com.gulimall.product.dto.SkuAttrDTO;
import com.gulimall.product.dto.SkuImageDTO;
import com.gulimall.product.dto.SkuMemberPriceDTO;
import com.gulimall.product.dto.SkuQueryDTO;
import com.gulimall.product.dto.SpuSkuDTO;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.mapper.SkuInfoMapper;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.service.SkuImagesService;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.service.SkuSaleAttrValueService;
import com.gulimall.product.service.SpuInfoDescService;
import com.gulimall.product.vo.SkuInfoVO;
import com.gulimall.product.vo.SkuItemVO;
import com.gulimall.product.vo.SpuBaseAttrVO;
import com.gulimall.product.vo.SpuSaleAttrVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
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

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private ProductStorageFeignClient productStorageFeignClient;

    @Autowired
    private ThreadPoolExecutor bizThreadPoolExecutor;


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


    /**
     * 查询sku集合
     *
     * @param spuId
     * @return
     */
    @Override
    public List<ProductPutawayEsDTO> listSkusBySpuId(String spuId) {
        return skuInfoMapper.listSkusBySpuId(spuId);
    }

    /**
     * 查询sku详细信息
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuItemVO getSkuDetailBySkuId(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVO skuItemVO = new SkuItemVO();

        // 1、sku基本信息
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity skuInfo = getById(skuId);
            skuItemVO.setInfo(skuInfo);
            return skuInfo;
        }, bizThreadPoolExecutor);

        // 2、sku图片信息
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> skuImages = skuImagesService.query().eq("sku_id", skuId).list();
            skuItemVO.setImages(skuImages);
        }, bizThreadPoolExecutor);


        // 3、库存信息
        CompletableFuture<Void> storageFuture = CompletableFuture.runAsync(() -> {
            R<List<SkuStorageDTO>> result = productStorageFeignClient.querySkuStorageBySkuIds(Arrays.asList(skuId));
            if (!result.isSuccess()) {
                log.error("查询商品库存失败，原因：{}", result.getMessage());
            }
            for (SkuStorageDTO skuStorageDTO : result.getData()) {
                if (skuId.equals(skuStorageDTO.getSkuId())) {
                    skuItemVO.setHasStock(skuStorageDTO.getHasStock());
                    break;
                }
            }
        }, bizThreadPoolExecutor);

        // 4、sku销售属性信息
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            List<SpuSaleAttrVO> saleAttrs = skuSaleAttrValueService.listSaleAttrsBySpuId(skuInfo.getSpuId());
            skuItemVO.setSaleAttrs(saleAttrs);
        });

        // 5、spu介绍信息
        CompletableFuture<Void> spuDescriptionFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.query().eq("spu_id", skuInfo.getSpuId()).one();
            skuItemVO.setDescription(spuInfoDesc);
        }, bizThreadPoolExecutor);

        // 5、spu规格参数信息
        CompletableFuture<Void> baseAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            Long spuId = skuInfo.getSpuId();
            Long catalogId = skuInfo.getCatalogId();
            List<SpuBaseAttrVO> baseAttrs = attrGroupService.listAttrGroupWithAttrsBySpuIdAndCatalogId(spuId, catalogId);
            skuItemVO.setSkuBaseAttrs(baseAttrs);
        });

        // CompletableFuture.allOf(imageFuture, storageFuture, saleAttrFuture, spuDescriptionFuture, baseAttrFuture)
        //         .exceptionally((exception) -> {
        //             if (exception != null) {
        //                 log.error("查询sku详情失败。原因：{}", exception.getMessage());
        //                 throw new GulimallException("查询sku详情失败。原因：" + exception.getMessage());
        //             }
        //             return "";
        //         });
        // 6、等待任务完成
        CompletableFuture.allOf(imageFuture, storageFuture, saleAttrFuture, spuDescriptionFuture, baseAttrFuture).get();

        return skuItemVO;
    }
}
