package com.Lhan.personal_blog.common.validator.constraint;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<NotBlank,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StringUtils.isBlank(value))
        {
            return false;
        }
        return true;
    }
}
