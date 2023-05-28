package com.gulimall.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gulimall.auth.constant.AuthCachConstants;
import com.gulimall.auth.service.SmsService;
import com.gulimall.auth.util.SmsUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    private final RedisTemplate redisTemplate;

    private final SmsUtils smsUtils;

    public SmsServiceImpl(RedisTemplate redisTemplate, SmsUtils smsUtils) {
        this.redisTemplate = redisTemplate;
        this.smsUtils = smsUtils;
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    @Override
    public void sendSmsCode(String phone) {
        // 1、生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 2、发送验证码
        smsUtils.sendSmsCode(phone, code);

        // 3、缓存验证码
        String key = AuthCachConstants.SMS_CODE_CACHE_PREFIX + phone;
        redisTemplate.opsForValue().set(key, code, AuthCachConstants.SMS_CODE_EXPIRED, TimeUnit.MINUTES);
    }
}
