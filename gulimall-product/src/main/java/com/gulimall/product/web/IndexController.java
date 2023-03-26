package com.gulimall.product.web;

import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoning
 * @date 2023/03/19
 */
@Controller
public class IndexController {

    private final CategoryService categoryService;

    public IndexController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/", "/index.html"})
    public String toIndex(Model model) {
        List<CategoryEntity> categories = categoryService.listTree();
        model.addAttribute("categories", categories);
        return "index";
    }

    @ResponseBody
    @GetMapping("/categories")
    public Map<String, List<Map<String, Object>>> listCategories() {
        List<CategoryEntity> categoryEntities = categoryService.listTree();
        Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            List<Map<String, Object>> category2 = processCategory2(categoryEntity);
            result.put(String.valueOf(categoryEntity.getCatId()), category2);
        }

        return result;
    }

    private List<Map<String, Object>> processCategory2(CategoryEntity categoryEntity) {
        List<Map<String, Object>> catalog2List = new ArrayList<>();
        for (CategoryEntity category : categoryEntity.getChildren()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("catalog1Id", String.valueOf(categoryEntity.getCatId()));
            map.put("id", String.valueOf(category.getCatId()));
            map.put("name", category.getName());
            List<Map<String, String>> catalog3List = processCategory3(category);
            map.put("catalog3List", catalog3List);

            catalog2List.add(map);
        }

        return catalog2List;
    }

    private List<Map<String, String>> processCategory3(CategoryEntity category) {
        List<Map<String, String>> catalog3List = new ArrayList<>();
        for (CategoryEntity child : category.getChildren()) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("catalog2Id", String.valueOf(category.getCatId()));
            map.put("id", String.valueOf(child.getCatId()));
            map.put("name", child.getName());

            catalog3List.add(map);
        }
        return catalog3List;
    }

}
