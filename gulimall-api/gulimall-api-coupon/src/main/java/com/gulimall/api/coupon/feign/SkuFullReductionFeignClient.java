package com.gulimall.api.coupon.feign;

import com.gulimall.api.coupon.dto.SkuFullReductionDTO;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * sku满多少钱减信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@FeignClient(value = "gulimall-coupon", contextId = "skuFullReduction")
public interface SkuFullReductionFeignClient {


    /**
     * 批量保存sku满减信息
     *
     * @param skuFullReductions sku满减信息集合
     * @return
     */
    @PostMapping("/coupon/skuFullReduction/save")
    R<?> saveSkuFullReductions(@Validated @RequestBody List<SkuFullReductionDTO> skuFullReductions);

}
