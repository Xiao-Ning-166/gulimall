package com.gulimall.storage.feign;

import com.gulimall.api.storage.feign.ProductStorageFeignClient;
import com.gulimall.api.storage.model.dto.SkuStorageDTO;
import com.gulimall.common.core.vo.R;
import com.gulimall.storage.service.WareSkuService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品存储服务
 *
 * @author xiaoning
 * @date 2023/03/16
 */
@RestController
public class ProductStorageFeignController implements ProductStorageFeignClient {

    private final WareSkuService wareSkuService;

    public ProductStorageFeignController(WareSkuService wareSkuService) {
        this.wareSkuService = wareSkuService;
    }

    /**
     * 查询商品库存
     *
     * @param skuIds
     * @return
     */
    @Override
    public R<List<SkuStorageDTO>> querySkuStorageBySkuIds(List<Long> skuIds) {
        List<SkuStorageDTO> storageDTOs = wareSkuService.listSkuWareBySkuIds(skuIds);
        return R.ok(storageDTOs);
    }
}
