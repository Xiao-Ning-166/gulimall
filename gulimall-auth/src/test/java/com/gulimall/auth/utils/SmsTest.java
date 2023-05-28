package com.gulimall.auth.utils;

import com.gulimall.auth.util.SmsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SmsTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testSendSms() {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "366759b6a8874621b7538a8119d739dd";
        // Map<String, String> headers = new HashMap<String, String>();

        HttpHeaders headers = new HttpHeaders();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.set("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<>();

        bodys.put("content", Arrays.asList("code:1234"));
        bodys.put("phone_number", Arrays.asList("13219522058"));
        bodys.put("template_id", Arrays.asList("CST_ptdie100"));


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(bodys, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.exchange(host + path, HttpMethod.POST, httpEntity, String.class);
            System.out.println(stringResponseEntity);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private SmsUtils smsUtils;

    @Test
    void testSmsUtil() {
        smsUtils.sendSmsCode("13219522058", "101");
    }
}
