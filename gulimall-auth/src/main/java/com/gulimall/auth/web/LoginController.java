package com.gulimall.auth.web;

import com.gulimall.api.member.dto.UserLoginDTO;
import com.gulimall.api.member.feign.MemberFeignClient;
import com.gulimall.common.core.vo.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author xiaoning
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final MemberFeignClient memberFeignClient;

    public LoginController(RedisTemplate redisTemplate, MemberFeignClient memberFeignClient) {
        this.memberFeignClient = memberFeignClient;
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping()
    public String login(UserLoginDTO userLoginDTO, RedirectAttributes attributes) {

        R<?> result = memberFeignClient.login(userLoginDTO);
        if (result.isSuccess()) {
            // 登录成功
            return "redirect:http://gulimall.com";
        }

        // 返回错误信息。将错误信息放入session中
        attributes.addFlashAttribute("errors", result.getMessage());
        // 重定向注册页面
        return "redirect:http://auth.gulimall.com/login.html";
    }

}
