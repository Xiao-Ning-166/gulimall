package com.gulimall.product.constant;

/**
 * @author xiaoning
 * @date 2023/03/26
 */
public interface ProductCacheConstants {

    /**
     * 分类数据缓存
     */
    String CATEGORY_CACHE = "category";

    /**
     * 缓存过期时间。1个小时，单位秒
     */
    Long CACHE_EXPIRE_TIME = 60 * 60 * 60L;

}
