package com.gulimall.coupon.feign;

import com.gulimall.api.coupon.dto.SkuLadderDTO;
import com.gulimall.api.coupon.feign.SkuLadderFeignClient;
import com.gulimall.common.core.vo.R;
import com.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaoning
 * @date 2023/02/13
 */
@RestController
public class SkuLadderFeignController implements SkuLadderFeignClient {

    @Autowired
    private SkuLadderService skuLadderService;

    /**
     * 批量保存sku满减信息
     *
     * @param skuLadders sku满减信息集合
     * @return
     */
    @Override
    public R<?> saveSkuLadders(List<SkuLadderDTO> skuLadders) {
        skuLadderService.saveSkuLadders(skuLadders);
        return R.success();
    }
}
