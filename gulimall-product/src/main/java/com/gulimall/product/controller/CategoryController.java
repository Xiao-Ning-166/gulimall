package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.utils.PageUtils;
import com.gulimall.common.utils.R;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 商品三级分类
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 以树形结构返回商品类型列表
     *
     * @param categoryEntity
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/list/tree")
    public R listWithTree(CategoryEntity categoryEntity,
                          @RequestParam(value = "current", defaultValue = "1") Integer current,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          HttpServletRequest request){
        IPage<CategoryEntity> page = new Page<>(current, size);
        IPage<CategoryEntity> categoryPage = categoryService.listWithTree(categoryEntity, page);

        return R.ok().put("data", categoryPage);
    }

    /**
     * 以树形结构返回商品类型列表（树形选择框）
     *
     * @return
     */
    @RequestMapping("/select/tree")
    public R listSelectTree(){
        List<CategoryEntity> categoryList = categoryService.listSelectTree();

        return R.ok().put("data", categoryList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改分类信息
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping()
    public R delete(@RequestBody Long[] catIds){

        categoryService.removeCategoryByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
