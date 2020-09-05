package com.Lhan.personal_blog.common.validator.annotion;

import com.Lhan.personal_blog.common.validator.Message;
import com.Lhan.personal_blog.common.validator.constraint.NumericValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

/**
 * 是否为数字
 */
@Target({ElementType.TYPE, ANNOTATION_TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumericValidator.class})
public @interface Numeric {

    String message() default Message.CK_NUMERIC_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
