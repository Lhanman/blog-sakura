package com.Lhan.personal_blog.common.validator.constraint;

import com.Lhan.personal_blog.common.validator.annotion.NotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<NotNull,Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null;
    }
}
