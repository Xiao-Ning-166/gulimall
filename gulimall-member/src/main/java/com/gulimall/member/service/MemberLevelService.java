package com.gulimall.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author xiaoning
 * @email 475529787@qq.com
 * @date 2022-10-24 20:27:59
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param memberLevelEntity 查询条件
     * @param page              分页参数
     * @return
     */
    IPage<MemberLevelEntity> listPage(MemberLevelEntity memberLevelEntity, IPage<MemberLevelEntity> page);
}

