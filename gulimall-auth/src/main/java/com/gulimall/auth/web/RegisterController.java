package com.gulimall.auth.web;

import cn.hutool.core.util.StrUtil;
import com.gulimall.api.member.dto.UserRegisterDTO;
import com.gulimall.api.member.feign.MemberFeignClient;
import com.gulimall.auth.constant.AuthCachConstants;
import com.gulimall.common.core.vo.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RedisTemplate redisTemplate;

    private final MemberFeignClient memberFeignClient;

    public RegisterController(RedisTemplate redisTemplate, MemberFeignClient memberFeignClient) {
        this.redisTemplate = redisTemplate;
        this.memberFeignClient = memberFeignClient;
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult result, RedirectAttributes attributes) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            // 校验未通过
            errors = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            // 返回错误信息。将错误信息放入session中
            attributes.addFlashAttribute("errors", errors);
            // 重定向注册页面
            return "redirect:http://auth.gulimall.com/register.html";
        }

        // 判断验证码
        String userCode = userRegisterDTO.getCode();
        String phone = userRegisterDTO.getPhone();
        boolean checkResult = checkUserRegisterCode(userCode, phone);
        if (!checkResult) {
            // 验证码校验失败
            errors.put("code", "验证码错误");
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/register.html";
        }

        // 进行远程调用，保存用户信息
        R<?> registerResult = memberFeignClient.register(userRegisterDTO);
        if (registerResult.isSuccess()) {
            // 调用成功，重定向登录页面
            return "redirect:http://auth.gulimall.com/login.html";
        }

        // 调用失败
        errors.put("msg", registerResult.getMessage());
        attributes.addFlashAttribute("errors", errors);
        // 重定向注册页面
        return "redirect:http://auth.gulimall.com/register.html";

    }

    /**
     * 校验用户注册验证码
     *
     * @param userCode 用户提交的验证码
     * @param phone 用户手机号
     * @return
     */
    private boolean checkUserRegisterCode(String userCode, String phone) {
        String key = AuthCachConstants.SMS_CODE_CACHE_PREFIX + phone;
        String realCode = String.valueOf(redisTemplate.opsForValue().get(key));
        // 如果验证码不存或者与用户提交验证码不相等，则校验失败
        if (StrUtil.isBlank(realCode) || !StrUtil.equals(userCode, realCode)) {
            return false;
        }

        // 验证码校验成功
        // 删除redis中的验证码
        redisTemplate.delete(key);
        // 返回校验成功结果
        return true;
    }

}
