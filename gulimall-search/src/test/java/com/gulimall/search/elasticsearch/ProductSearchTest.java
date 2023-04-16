package com.gulimall.search.elasticsearch;

import com.gulimall.search.model.dto.SearchParamDTO;
import com.gulimall.search.model.vo.ProductSearchResultVO;
import com.gulimall.search.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiaoning
 * @date 2023/04/09
 */
@SpringBootTest
@DisplayName("商品搜索")
public class ProductSearchTest {

    @Resource
    private SearchService searchService;

    @Test
    void testSearch() throws Exception {
        SearchParamDTO searchParamDTO = new SearchParamDTO();
        searchParamDTO.setKeyword("iPhone");
        // searchParamDTO.setCatalog3Id(225L);
        // searchParamDTO.setBrandId(Arrays.asList(4L));
        // searchParamDTO.setSkuPrice("500_");
        // searchParamDTO.setCurrent(1);
        ProductSearchResultVO searchResultVO = searchService.search(searchParamDTO);
    }
}
