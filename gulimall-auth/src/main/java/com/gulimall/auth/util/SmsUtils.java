package com.gulimall.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Component
public class SmsUtils {

    private final RestTemplate restTemplate;

    @Value("${third.sms.host}")
    private String host;

    @Value("${third.sms.path}")
    private String path;

    @Value("${third.sms.appCode}")
    private String appCode;

    @Value("${third.sms.templateId}")
    private String templateId;

    public SmsUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 发送短信验证码
     *
     * @param phone 目标手机号
     * @param code  验证码
     */
    public void sendSmsCode(String phone, String code) {
        HttpHeaders headers = new HttpHeaders();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.set("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<>();

        bodys.put("content", Arrays.asList("code:" + code));
        bodys.put("phone_number", Arrays.asList(phone));
        bodys.put("template_id", Arrays.asList(templateId));

        try {
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(bodys, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.exchange(host + path, HttpMethod.POST, httpEntity, String.class);
            if (stringResponseEntity.getStatusCode() != HttpStatus.OK) {
                log.error("短信验证码发送失败，原因：{}", stringResponseEntity.toString());
            }
        } catch (Exception e) {
            log.error("短信验证码程序出错，原因：{}", e.getMessage(), e);
        }
    }

}
