package com.gulimall.api.coupon.feign;

import com.gulimall.api.coupon.dto.MemberPriceDTO;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * sku会员价格信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@FeignClient(value = "gulimall-coupon", contextId = "skuMemberPrice")
public interface SkuMemberPriceFeignClient {

    /**
     * 批量保存sku会员价格信息
     *
     * @param memberPrices 会员价格信息集合
     * @return
     */
    @PostMapping("/coupon/skuMemberPrice/save")
    R<?> saveSkuMemberPrices(@Validated @RequestBody List<MemberPriceDTO> memberPrices);
}
