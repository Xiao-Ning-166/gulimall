package com.gulimall.product.feign;

import com.gulimall.api.product.feign.SkuInfoFeignClient;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.service.SkuInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/02/24
 */
@RestController
public class SkuInfoFeignController implements SkuInfoFeignClient {

    @Resource
    private SkuInfoService skuInfoService;

    /**
     * 批量保存sku满减信息
     *
     * @param skuIds sku满减信息集合
     * @return
     */
    @Override
    public R<?> getSkuInfoByIds(List<Long> skuIds) {
        List<SkuInfoEntity> entities = skuInfoService.query().in("sku_id", skuIds).list();
        return R.ok(entities);
    }
}
