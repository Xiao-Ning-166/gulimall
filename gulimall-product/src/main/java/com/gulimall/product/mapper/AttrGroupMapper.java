package com.gulimall.product.mapper;

import com.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.vo.AttrGroupResponseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性分组
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-23 19:07:59
 */
@Mapper
public interface AttrGroupMapper extends BaseMapper<AttrGroupEntity> {

    /**
     * 查询分类下的所有属性分组和属性分组下的属性列表
     *
     * @param catelogId 分类id
     * @return
     */
    List<AttrGroupResponseVO> listAttrGroupWithAttr(Long catelogId);
}
