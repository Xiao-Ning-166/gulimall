package com.gulimall.api.search.feign;

import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.common.core.config.FeignConfig;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品上架远程调用客户端
 *
 * @author xiaoning
 * @date 2023/03/13
 */
@FeignClient(value = "gulimall-search",
        contextId = "product-putaway",
        configuration = FeignConfig.class,
        path = "search")
public interface ProductEsFeignClient {

    /**
     * 商品上架
     *
     * @param productPutawayEsDTOs
     * @return
     */
    @PostMapping("/feign/product-putaway")
    public R<?> productPutaway(@RequestBody List<ProductPutawayEsDTO> productPutawayEsDTOs);

}
