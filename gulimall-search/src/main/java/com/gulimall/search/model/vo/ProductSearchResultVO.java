package com.gulimall.search.model.vo;

import com.gulimall.api.search.model.dto.ProductPutawayEsDTO;
import lombok.Data;

import java.util.List;

/**
 * 商品查询返回结果对象
 *
 * @author xiaoning
 * @date 2023/04/08
 */
@Data
public class ProductSearchResultVO {

    /**
     * 商品列表
     */
    private List<ProductPutawayEsDTO> products;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 结果涉及的品牌信息
     */
    private List<BrandVO> brands;

    /**
     * 结果涉及的分类信息
     */
    private List<CategoryVO> categories;

    /**
     * 结果涉及的属性信息
     */
    private List<AttrVO> attrs;
}
