package com.gulimall.product.mapper;

import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌分类关联
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface CategoryBrandRelationMapper extends BaseMapper<CategoryBrandRelationEntity> {

    /**
     * 查询品牌关联的分类列表
     *
     * @param brandId
     * @return
     */
    List<CategoryBrandRelationEntity> getRelationCategoryList(Long brandId);
}
