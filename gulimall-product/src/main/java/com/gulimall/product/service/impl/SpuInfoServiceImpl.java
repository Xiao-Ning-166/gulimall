package com.gulimall.product.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.coupon.dto.SpuBoundDTO;
import com.gulimall.api.coupon.feign.SpuBoundFeignClient;
import com.gulimall.product.dto.SpuBaseAttrDTO;
import com.gulimall.product.dto.SpuBoundsDTO;
import com.gulimall.product.dto.SpuQueryDTO;
import com.gulimall.product.dto.SpuSaveDTO;
import com.gulimall.product.dto.SpuSkuDTO;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.mapper.SpuInfoMapper;
import com.gulimall.product.service.ProductAttrValueService;
import com.gulimall.product.service.SkuInfoService;
import com.gulimall.product.service.SpuImagesService;
import com.gulimall.product.service.SpuInfoDescService;
import com.gulimall.product.service.SpuInfoService;
import com.gulimall.product.vo.SpuInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * spu信息
 *
 * @author xiaoning
 * @date 2022-10-23 19:07:59
 */
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuBoundFeignClient spuBoundFeignClient;

    /**
     * 发布商品
     * // TODO 分布式事务、失败重试
     *
     * @param spuSaveDTO 商品信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishProduct(SpuSaveDTO spuSaveDTO) {
        // 1、保存spu信息
        // 1.1、保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveDTO, spuInfoEntity);
        spuInfoEntity.setCatalogId(spuSaveDTO.getCatelogId());
        spuInfoEntity.setCreateTime(DateUtil.date());
        spuInfoEntity.setUpdateTime(DateUtil.date());
        spuInfoMapper.insert(spuInfoEntity);

        // 1.2、保存spu图片信息 pms_spu_images
        List<String> spuImages = spuSaveDTO.getImages();
        spuImagesService.saveSpuImagesBatch(spuImages, spuInfoEntity.getId());

        // 1.3、保存spu描述信息 pms_spu_info_desc
        List<String> descriptionImageUrls = spuSaveDTO.getDecript();
        spuInfoDescService.saveDescriptionImagesBatch(descriptionImageUrls, spuInfoEntity.getId());

        // 2、保存spu属性信息（规格参数）pms_product_attr_value
        List<SpuBaseAttrDTO> spuBaseAttrs = spuSaveDTO.getBaseAttrs();
        productAttrValueService.saveSpuAttrs(spuBaseAttrs, spuInfoEntity.getId());

        // 3、保存spu sku信息
        List<SpuSkuDTO> skus = spuSaveDTO.getSkus();
        skuInfoService.saveSkus(skus, spuInfoEntity);

        // 4、保存spu积分信息 gulimall-coupon.sms_spu_bounds
        SpuBoundDTO spuBoundDTO = new SpuBoundDTO();
        SpuBoundsDTO bounds = spuSaveDTO.getBounds();
        spuBoundDTO.setSpuId(spuInfoEntity.getId());
        spuBoundDTO.setBuyBounds(bounds.getBuyBounds());
        spuBoundDTO.setGrowBounds(bounds.getGrowBounds());
        spuBoundFeignClient.saveSpuBound(spuBoundDTO);
    }

    /**
     * 分页查询spu信息
     *
     * @param spuQueryDTO 查询条件
     * @param page        分页信息
     * @return
     */
    @Override
    public IPage<SpuInfoVO> queryPage(SpuQueryDTO spuQueryDTO, IPage<SpuInfoVO> page) {
        return spuInfoMapper.queryPage(spuQueryDTO, page);
    }

}
