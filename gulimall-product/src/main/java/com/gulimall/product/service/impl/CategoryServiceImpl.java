package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.utils.PageUtils;
import com.gulimall.common.utils.Query;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 以树形结构返回类型列表
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 1、查询所有分类
        List<CategoryEntity> allCategoryList = query().list();

        // 2、组装分类树形结构
        // 2.1、找到一级分类
        List<CategoryEntity> topCategoryList = allCategoryList.stream().filter((category) -> {
            // 返回一级分类
            return category.getParentCid() == 0;
        }).map((category) -> {
            // 查找所有子分类
            category.setChildren(getChildren(category, allCategoryList));
            return category;
        }).sorted((category1, category2) -> {
            // 分类进行排序
            return (category1.getSort() == null ? 0 : category1.getSort()) - (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList());

        // 3、返回一级分类集合
        return topCategoryList;
    }

    /**
     * 获取目标分类的所有子分类
     *
     * @param currentCategory 目标分类
     * @param allCategoryList 所有分类
     * @return 目标分类的子分类集合（包含子分类的子分类）
     */
    private List<CategoryEntity> getChildren(CategoryEntity currentCategory, List<CategoryEntity> allCategoryList) {

        List<CategoryEntity> children = allCategoryList.stream().filter((category) -> {
            // 找到目标分类的所有子分类
            return category.getParentCid().equals(currentCategory.getCatId());
        }).map((category) -> {
            // 找到子分类的子分类
            category.setChildren(getChildren(category, allCategoryList));
            return category;
        }).sorted((category1, category2) -> {
            // 将子分类进行排序
            return (category1.getSort() == null ? 0 : category1.getSort()) - (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList());

        // 返回子分类集合
        return children;
    }

}
