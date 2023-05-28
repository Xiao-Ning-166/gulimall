package com.gulimall.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.api.member.dto.UserLoginDTO;
import com.gulimall.api.member.dto.UserRegisterDTO;
import com.gulimall.common.core.exception.PhoneNotUniqueException;
import com.gulimall.common.core.exception.UsernameNotUniqueException;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.mapper.MemberMapper;
import com.gulimall.member.service.MemberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 注册
     *
     * @param userRegisterDTO
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) throws UsernameNotUniqueException, PhoneNotUniqueException {
        // 1、检查用户名是否唯一
        String username = userRegisterDTO.getUsername();
        checkUsernameUnique(username);

        // 2、检查手机号是否唯一
        String phone = userRegisterDTO.getPhone();
        checkPhoneUnique(phone);

        MemberEntity member = new MemberEntity();
        member.setUsername(username);
        member.setMobile(phone);
        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        member.setPassword(encodePassword);

        // 保存用户信息
        save(member);
    }

    /**
     * 检查用户名是否唯一
     *
     * @param username
     */
    private void checkUsernameUnique(String username) {
        Integer sameUsernameCount = this.query().eq("username", username).count();
        if (sameUsernameCount > 0) {
            throw new UsernameNotUniqueException();
        }
    }

    /**
     * 检查手机号是否唯一
     *
     * @param phone
     */
    private void checkPhoneUnique(String phone) {
        Integer samePhoneCount = this.query().eq("mobile", phone).count();
        if (samePhoneCount > 0) {
            throw new PhoneNotUniqueException();
        }
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public MemberEntity login(UserLoginDTO userLoginDTO) {
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        MemberEntity memberEntity = query().eq("username", account).or().eq("mobile", account).one();
        if (ObjectUtil.isNull(memberEntity)) {
            return null;
        }
        // 匹配密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatch = passwordEncoder.matches(password, memberEntity.getPassword());
        if (isMatch) {
            // 匹配成功
            return memberEntity;
        }
        return null;
    }
}
