package com.gulimall.api.member.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册对象
 */
@Data
public class UserRegisterDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20)
    String password;

    /**
     * 手机号
     */
    @NotBlank(message = "手机不能为空")
    @Pattern(regexp ="^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    String code;

}
