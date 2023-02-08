package com.gulimall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.coupon.entity.HomeSubjectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoning
* @description 针对表【sms_home_subject(首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】)】的数据库操作Mapper
* @createDate 2023-02-04 22:05:58
* @Entity com.gulimall.coupon.entity.HomeSubject
*/
@Mapper
public interface HomeSubjectMapper extends BaseMapper<HomeSubjectEntity> {

}




