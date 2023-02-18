package com.gulimall.coupon.feign;

import com.gulimall.api.coupon.dto.MemberPriceDTO;
import com.gulimall.api.coupon.feign.SkuMemberPriceFeignClient;
import com.gulimall.common.core.vo.R;
import com.gulimall.coupon.service.MemberPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * sku会员价格信息
 *
 * @author xiaoning
 * @date 2023/02/13
 */
@RestController
public class SkuMemberPriceFeignController implements SkuMemberPriceFeignClient {

    @Autowired
    private MemberPriceService memberPriceService;

    /**
     * 批量保存sku会员价格信息
     *
     * @param memberPrices 会员价格信息集合
     * @return
     */
    @Override
    public R<?> saveSkuMemberPrices(List<MemberPriceDTO> memberPrices) {
        memberPriceService.saveMemberPrices(memberPrices);
        return R.success();
    }
}
