package com.gulimall.common.database.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段处理。使用MyBatis-plus的自动填充功能
 *
 * @author xiaoning
 * @date 2023/02/19
 */
@Slf4j
@Component
public class MyBatisPlusCommonFieldHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始处理插入时要填充的字段......");
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始处理更新时要填充的字段......");
        metaObject.setValue("updateTime", new Date());
    }
}
