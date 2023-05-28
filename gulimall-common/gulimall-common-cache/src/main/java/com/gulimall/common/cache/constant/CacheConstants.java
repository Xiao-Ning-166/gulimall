package com.gulimall.common.cache.constant;

/**
 * @author xiaoning
 * @date 2023/03/26
 */
public interface CacheConstants {

    /**
     * 缓存根前缀
     */
    String CACHE_ROOT_PREFIX = "cache";

    /**
     * 商品服务缓存前缀
     */
    String PRODUCT_CACHE_PREFIX = "product";

    /**
     * 认证服务缓存前缀
     */
    String AUTH_CACHE_PREFIX = "auth";

    /**
     * 缓存key名称分隔符
     */

    String CACHE_KEY_SEPARATOR = ":";

    /**
     * 防刷前缀
     */
    String BRUSH_PROOF_PREFIX = "access" + CACHE_KEY_SEPARATOR;

    /**
     * 首次访问次数
     */
    Long FIRST_ACCESS_COUNT = 1L;

}
