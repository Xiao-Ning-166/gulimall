package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;



/**
 * 商品三级分类
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 以树形结构返回商品类型列表
     *
     * @param categoryEntity
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/list/tree")
    @ApiOperation(value = "以树形结构返回类型列表", notes = "以树形结构返回类型列表（带分页）", response = R.class)
    public R listWithTree(CategoryEntity categoryEntity,
                          @RequestParam(value = "current", defaultValue = "1") Integer current,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          HttpServletRequest request){
        IPage<CategoryEntity> page = new Page<>(current, size);
        IPage<CategoryEntity> categoryPage = categoryService.listWithTree(categoryEntity, page);

        return R.ok(categoryPage);
    }

    /**
     * 以树形结构返回商品类型列表（树形选择框）
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "以树形结构返回类型列表", notes = "以树形结构返回类型列表（不带分页）", response = R.class)
    public R listTree(){
        List<CategoryEntity> categoryList = categoryService.listTree();

        return R.ok(categoryList);
    }

    /**
     * 以树形结构返回商品类型列表（树形选择框）
     *
     * @return
     */
    @RequestMapping("/select/tree")
    @ApiOperation(value = "以树形结构返回类型列表", notes = "以树形结构返回类型列表", response = R.class)
    public R listSelectTree(){
        List<CategoryEntity> categoryList = categoryService.listSelectTree();

        return R.ok(categoryList);
    }

    /**
     * 保存
     */
    @GetMapping("/save")
    @ApiOperation(value = "保存分类信息", notes = "保存分类信息", response = R.class)
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.success();
    }

    /**
     * 修改分类信息
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息", response = R.class)
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping()
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息", response = R.class)
    public R delete(@RequestBody Long[] catIds){

        categoryService.removeCategoryByIds(Arrays.asList(catIds));

        return R.success();
    }

}
