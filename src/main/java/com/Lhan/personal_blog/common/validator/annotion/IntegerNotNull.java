package com.Lhan.personal_blog.common.validator.annotion;


import com.Lhan.personal_blog.common.validator.Message;
import com.Lhan.personal_blog.common.validator.constraint.IntegerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Integer值不为空
 */

@Target({TYPE, ANNOTATION_TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IntegerValidator.class})
public @interface IntegerNotNull {

    String message() default Message.CK_NUMERIC_DEFAULT;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
