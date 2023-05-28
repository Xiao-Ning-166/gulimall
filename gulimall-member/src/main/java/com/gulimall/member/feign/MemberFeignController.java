package com.gulimall.member.feign;

import cn.hutool.core.util.ObjectUtil;
import com.gulimall.api.member.dto.UserLoginDTO;
import com.gulimall.api.member.dto.UserRegisterDTO;
import com.gulimall.api.member.feign.MemberFeignClient;
import com.gulimall.common.core.constant.ResponseCodeEnum;
import com.gulimall.common.core.exception.PhoneNotUniqueException;
import com.gulimall.common.core.exception.UsernameNotUniqueException;
import com.gulimall.common.core.vo.R;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberFeignController implements MemberFeignClient {

    private final MemberService memberService;

    public MemberFeignController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册信息
     * @return
     */
    @Override
    public R<?> register(UserRegisterDTO userRegisterDTO) {
        try {
            memberService.register(userRegisterDTO);
            return R.success();
        } catch (UsernameNotUniqueException e) {
            log.error("注册失败，原因：{}", e.getMessage(), e);
            return R.error(ResponseCodeEnum.USERNAME_NOT_UNIQUE_10401);
        } catch (PhoneNotUniqueException e) {
            log.error("注册失败，原因：{}", e.getMessage(), e);
            return R.error(ResponseCodeEnum.PHONE_NOT_UNIQUE_10402);
        }
    }

    @Override
    public R<?> login(UserLoginDTO userLoginDTO) {
        MemberEntity member = null;
        try {
            member = memberService.login(userLoginDTO);
            if (ObjectUtil.isNull(member)) {
                // 登录失败
                return R.error(ResponseCodeEnum.USER_LOGIN_FAILED_10403);
            }
            return R.success();
        } catch (Exception e) {
            log.error("登录失败，原因：{}", e.getMessage(), e);
            return R.error(ResponseCodeEnum.USER_LOGIN_FAILED_10403);
        }

    }
}
