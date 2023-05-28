package com.gulimall.auth.service;

public interface SmsService {

    /**
     * 发送验证码
     *
     * @param phone
     */
    void sendSmsCode(String phone);
}
