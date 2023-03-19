package com.gulimall.search.service;

import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaoning
 * @date 2023/03/15
 */
public interface ProductEsService {

    /**
     * 商品上架
     *
     * @param productPutawayEsDTOs
     */
    boolean putaway(List<ProductPutawayEsDTO> productPutawayEsDTOs) throws IOException;
}
