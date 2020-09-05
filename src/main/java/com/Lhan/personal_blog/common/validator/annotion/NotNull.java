package com.Lhan.personal_blog.common.validator.annotion;

import com.Lhan.personal_blog.common.validator.constraint.IdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * id值不为空
 */
@Target({TYPE, ANNOTATION_TYPE,FIELD,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdValidator.class})
public @interface NotNull {
    String message() default "";

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
