package com.gulimall.auth.web;

import com.gulimall.common.cache.aspect.annotation.BrushProof;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    /**
     * 跳转注册页面
     *
     * @return
     */
    @GetMapping("/register.html")
    public String toRegisterPage() {
        return "register";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping("/login.html")
    public String toLoginPage() {
        return "login";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @BrushProof
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
