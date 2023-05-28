package com.gulimall.api.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 */
@Data
public class UserLoginDTO {

    /**
     * 账户
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
