package com.Lhan.personal_blog.common.validator.annotion;

import com.Lhan.personal_blog.common.validator.Message;
import com.Lhan.personal_blog.common.validator.constraint.StringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

import static java.lang.annotation.ElementType.*;

/**
 * 不是空字符串
 */
@Target({TYPE, ANNOTATION_TYPE,FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StringValidator.class})
public @interface NotBlank {
    String message() default Message.CK_NOT_BLANK_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
