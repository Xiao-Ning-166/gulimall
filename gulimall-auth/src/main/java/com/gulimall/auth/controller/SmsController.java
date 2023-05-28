package com.gulimall.auth.controller;

import com.gulimall.auth.service.SmsService;
import com.gulimall.common.cache.aspect.annotation.BrushProof;
import com.gulimall.common.core.vo.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @BrushProof
    @PostMapping("/code")
    public R<?> sendSmsCode(String phone) {
        smsService.sendSmsCode(phone);
        return R.success();
    }

}
