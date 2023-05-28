package com.gulimall.common.cache.aspect.annotation;

import java.lang.annotation.*;

/**
 * 防刷注解
 * @author xiaoning
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface BrushProof {

    /**
     * 有效期。单位：秒，默认60s
     *
     * @return
     */
    int expire() default 60;

    /**
     * 有效期内允许访问多少次
     *
     * @return
     */
    int limit() default 3;
}
