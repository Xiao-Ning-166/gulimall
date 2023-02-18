package com.gulimall.api.coupon.feign;

import com.gulimall.api.coupon.dto.SpuBoundDTO;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * spu积分信息远程调用接口
 * contextId：用来唯一标识当一个微服务中存在多个FeignClient接口调用同一个服务提供者时的场景
 *
 * @author xiaoning
 * @date 2023/02/06
 */
@FeignClient(value = "gulimall-coupon", contextId = "spuBound")
public interface SpuBoundFeignClient {

    /**
     * 保存spu积分信息
     *
     * @param spuBoundDTO spu积分参数
     * @return
     */
    @PostMapping("/coupon/spuBound/save")
    R<?> saveSpuBound(@Validated @RequestBody SpuBoundDTO spuBoundDTO);

}
