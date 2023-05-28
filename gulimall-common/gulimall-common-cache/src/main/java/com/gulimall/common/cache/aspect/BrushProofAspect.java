package com.gulimall.common.cache.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.gulimall.common.cache.aspect.annotation.BrushProof;
import com.gulimall.common.cache.constant.CacheConstants;
import com.gulimall.common.core.exception.GulimallException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 接口防刷切面类
 * @author xiaoning
 */
@Aspect
@Component
public class BrushProofAspect {

    private final RedisTemplate redisTemplate;

    public BrushProofAspect(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(com.gulimall.common.cache.aspect.annotation.BrushProof)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public void before(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求地址、来源ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI().toString();
        String remoteHost = getRemoteHost(request);

        // 获取方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        BrushProof annotation = method.getAnnotation(BrushProof.class);
        int expired = annotation.expire();
        int limit = annotation.limit();

        String key = CacheConstants.BRUSH_PROOF_PREFIX + remoteHost + CacheConstants.CACHE_KEY_SEPARATOR + url;
        Object accessCount = redisTemplate.opsForValue().get(key);
        if (ObjectUtil.isNull(accessCount)) {
            // 首次访问
            redisTemplate.opsForValue().set(key, CacheConstants.FIRST_ACCESS_COUNT, expired, TimeUnit.SECONDS);
        } else {
            // 非首次访问
            // 判断访问次数
            Integer count = Convert.toInt(accessCount);
            if (count > limit) {
                // 超过
                throw new GulimallException("访问过于频繁");
            } else {
                redisTemplate.opsForValue().increment(key);
            }
        }

        // 执行方法
        joinPoint.proceed();
    }

    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
