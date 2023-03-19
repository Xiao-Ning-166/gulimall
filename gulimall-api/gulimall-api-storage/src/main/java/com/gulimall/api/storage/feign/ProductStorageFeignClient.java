package com.gulimall.api.storage.feign;

import com.gulimall.api.storage.model.dto.SkuStorageDTO;
import com.gulimall.common.core.config.FeignConfig;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品库存远程调用客户端
 *
 * @author xiaoning
 * @date 2023/03/16
 */
@FeignClient(value = "gulimall-storage",
        contextId = "product-storage",
        configuration = FeignConfig.class,
        path = "storage")
public interface ProductStorageFeignClient {

    /**
     * 查询商品库存
     *
     * @param skuIds
     * @return
     */
    @GetMapping("/feign/sku-stocks")
    R<List<SkuStorageDTO>> querySkuStorageBySkuIds(@RequestParam("skuIds") List<Long> skuIds);

}
