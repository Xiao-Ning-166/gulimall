package com.gulimall.search.feign;

import com.gulimall.api.search.feign.ProductEsFeignClient;
import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import com.gulimall.common.core.vo.R;
import com.gulimall.search.service.ProductEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/03/13
 */
@Slf4j
@RestController
public class ProductEsFeignController implements ProductEsFeignClient {

    private final ProductEsService productEsService;

    public ProductEsFeignController(ProductEsService productPutawayEsService) {
        this.productEsService = productPutawayEsService;
    }

    /**
     * 商品上架
     *
     * @param productPutawayEsDTOs
     * @return
     */
    @Override
    public R<?> productPutaway(List<ProductPutawayEsDTO> productPutawayEsDTOs) {
        try {
            productEsService.putaway(productPutawayEsDTOs);
        } catch (IOException e) {
            log.error("商品上架失败。原因：{}", e.getMessage());
            return R.error("商品上架失败。原因：{}" + e.getMessage());
        }
        return R.success();
    }
}
