package com.gulimall.auth.constant;

import com.gulimall.common.cache.constant.CacheConstants;

public interface AuthCachConstants {

    /**
     * 短信验证码缓存前缀
     */
    String SMS_CODE_CACHE_PREFIX = CacheConstants.CACHE_ROOT_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR +
            CacheConstants.AUTH_CACHE_PREFIX + "sms" + CacheConstants.CACHE_KEY_SEPARATOR + "code" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 短信验证码过期时间
     */
    Long SMS_CODE_EXPIRED = 5L;
}
