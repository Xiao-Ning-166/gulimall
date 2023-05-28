package com.gulimall.api.member.feign;


import com.gulimall.api.member.dto.UserLoginDTO;
import com.gulimall.api.member.dto.UserRegisterDTO;
import com.gulimall.common.core.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiaoning
 */
@FeignClient(value = "gulimall-member", contextId = "member-info", path = "member")
public interface MemberFeignClient {

    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册信息
     * @return
     */
    @PostMapping("/feign/register")
    R<?> register(@RequestBody @Validated UserRegisterDTO userRegisterDTO);

    /**
     * 账号密码登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/feign/login")
    R<?> login(@RequestBody @Validated UserLoginDTO userLoginDTO);
}
