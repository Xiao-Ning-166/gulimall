package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.core.utils.PageUtils;
import com.gulimall.common.core.utils.Query;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.member.mapper.MemberLevelMapper;
import com.gulimall.member.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevelEntity> implements MemberLevelService {

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                new QueryWrapper<MemberLevelEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询
     *
     * @param memberLevelEntity 查询条件
     * @param page              分页参数
     * @return
     */
    @Override
    public IPage<MemberLevelEntity> listPage(MemberLevelEntity memberLevelEntity, IPage<MemberLevelEntity> page) {
        QueryWrapper<MemberLevelEntity> queryWrapper = new QueryWrapper<>(memberLevelEntity);
        IPage<MemberLevelEntity> memberLevelPage = memberLevelMapper.selectPage(page, queryWrapper);

        return memberLevelPage;
    }

}
