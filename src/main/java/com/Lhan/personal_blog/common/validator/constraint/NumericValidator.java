package com.Lhan.personal_blog.common.validator.constraint;

import com.Lhan.personal_blog.common.validator.annotion.Numeric;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value  == null || StringUtils.isBlank(value))
        {
            return false;
        }
        if (!StringUtils.isNumeric(value))
        {
            return false;
        }
        return true;
    }
}
