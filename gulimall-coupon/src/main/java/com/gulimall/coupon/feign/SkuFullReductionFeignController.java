package com.gulimall.coupon.feign;

import com.gulimall.api.coupon.dto.SkuFullReductionDTO;
import com.gulimall.api.coupon.feign.SkuFullReductionFeignClient;
import com.gulimall.common.core.vo.R;
import com.gulimall.coupon.service.SkuFullReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * sku满减信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@RestController
public class SkuFullReductionFeignController implements SkuFullReductionFeignClient {

    @Autowired
    private SkuFullReductionService skuFullReductionService;

    /**
     * 批量保存sku满减信息
     *
     * @param skuFullReductions sku满减信息集合
     * @return
     */
    @Override
    public R<?> saveSkuFullReductions(List<SkuFullReductionDTO> skuFullReductions) {
        skuFullReductionService.saveSkuFullReductions(skuFullReductions);
        return R.success();
    }
}
