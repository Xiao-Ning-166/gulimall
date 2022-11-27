package com.gulimall.common.core.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验注解（值有多种情况）
 *
 * @author xiaoning
 * @date 2022/11/27
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { SelectConstraintValidatorForInteger.class })
public @interface Select {

    String message() default "{com.gulimall.common.core.valid.Select.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] values() default { };

}
