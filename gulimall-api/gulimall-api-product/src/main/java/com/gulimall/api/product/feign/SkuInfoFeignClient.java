package com.gulimall.api.product.feign;

import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiaoning
 * @date 2023/02/24
 */
@FeignClient(value = "gulimall-product", contextId = "skuInfo")
public interface SkuInfoFeignClient {

    /**
     * 批量保存sku满减信息
     *
     * @param skuIds sku满减信息集合
     * @return
     */
    @GetMapping("/product/skuinfo/skus")
    R<?> getSkuInfoByIds(@RequestParam("skuIds") List<Long> skuIds);

}
