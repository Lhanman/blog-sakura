package com.Lhan.personal_blog.common.validator.constraint;

import com.Lhan.personal_blog.common.validator.annotion.IntegerNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<IntegerNotNull,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null;
    }
}
