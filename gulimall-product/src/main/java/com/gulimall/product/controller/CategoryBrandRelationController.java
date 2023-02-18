package com.gulimall.product.controller;

import com.gulimall.common.core.vo.R;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.service.CategoryBrandRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;



/**
 * 品牌分类关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@RestController
@RequestMapping("/categorybrandrelation")
@Api(tags = "分类品牌接口")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 保存品牌分类关联信息
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存品牌分类关联信息", notes = "保存品牌分类关联信息", response = R.class)
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.save(categoryBrandRelation);

        return R.success();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "删除品牌分类关联", notes = "删除品牌分类关联", response = R.class)
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.success();
    }

    /**
     * 查询品牌关联的分类集合
     *
     * @param brandId
     * @return
     */
    @GetMapping("/category/{brandId}")
    @ApiOperation(value = "根据品牌id查询关联的分类列表", notes = "根据品牌id查询关联的分类列表", response = R.class)
    public R getRelationCategoryList(@PathVariable("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.getRelationCategoryList(brandId);
        return R.ok(data);
    }


    /**
     * 查询指定分类下的品牌集合
     *
     * @param categoryId 分类id
     * @return
     */
    @GetMapping("/brand/{categoryId}")
    @ApiOperation(value = "根据分类id查询关联的品牌列表", notes = "根据分类id查询关联的品牌列表", response = R.class)
    public R getBrandsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<BrandEntity> data = categoryBrandRelationService.getBrandsByCategory(categoryId);
        return R.ok(data);
    }
}
