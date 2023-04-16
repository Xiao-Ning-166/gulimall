package com.gulimall.search.service;

import com.gulimall.search.model.dto.SearchParamDTO;
import com.gulimall.search.model.vo.ProductSearchResultVO;

/**
 * @author xiaoning
 * @date 2023/04/08
 */
public interface SearchService {

    /**
     * 查询
     *
     * @param searchParamDTO
     * @return
     */
    ProductSearchResultVO search(SearchParamDTO searchParamDTO);
}
