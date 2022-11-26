package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 以树形结构返回商品类型列表
     *
     * @param categoryEntity
     * @param page
     * @return
     */
    IPage<CategoryEntity> listWithTree(CategoryEntity categoryEntity, IPage<CategoryEntity> page);

    /**
     * 通过id批量删除类别
     *
     * @param ids
     */
    void removeCategoryByIds(List<Long> ids);

    /**
     * 返回1、2级类别数据
     *
     * @return
     */
    List<CategoryEntity> listSelectTree();
}

