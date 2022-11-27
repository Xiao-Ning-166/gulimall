package com.gulimall.common.core.valid;

import org.bouncycastle.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验器
 *
 * @author xiaoning
 * @date 2022/11/27
 */
public class SelectConstraintValidatorForInteger implements ConstraintValidator<Select, Integer> {

    private List<Integer> list = new ArrayList<>();

    /**
     * 初始化校验器
     *
     */
    @Override
    public void initialize(Select constraintAnnotation) {
        // 获取规定值
        int[] values = constraintAnnotation.values();
        if (Arrays.isNullOrEmpty(values)) {
            // 没有值
            return;
        }
        // 加入集合
        for (int value : values) {
            list.add(value);
        }
    }

    /**
     * 判断校验是否成功
     *
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 判断是否在集合中
        return list.contains(value);
    }
}
