package com.gulimall.search.web;

import com.gulimall.search.model.dto.SearchParamDTO;
import com.gulimall.search.model.vo.ProductSearchResultVO;
import com.gulimall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiaoning
 * @date 2023/04/06
 */
@Controller
public class IndexController {

    @Autowired
    private SearchService searchService;

    @GetMapping({"/", "/list.html"})
    public String toIndex(SearchParamDTO searchParamDTO, Model model) {

        ProductSearchResultVO productSearchResultVO = searchService.search(searchParamDTO);

        model.addAttribute("result", productSearchResultVO);
        return "list";
    }

}
