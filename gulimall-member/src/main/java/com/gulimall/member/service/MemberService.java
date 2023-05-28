package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.api.member.dto.UserLoginDTO;
import com.gulimall.api.member.dto.UserRegisterDTO;
import com.gulimall.common.core.exception.PhoneNotUniqueException;
import com.gulimall.common.core.exception.UsernameNotUniqueException;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 20:27:59
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 注册
     *
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO) throws UsernameNotUniqueException, PhoneNotUniqueException;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    MemberEntity login(UserLoginDTO userLoginDTO);
}

