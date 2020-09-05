package com.Lhan.personal_blog.common.validator.constraint;

import com.Lhan.personal_blog.common.validator.Message;
import com.Lhan.personal_blog.common.validator.annotion.Range;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<Range,String> {

    private long min;

    private long max;

    private final int DEFAULT_MAX = 11;

    @Override
    public void initialize(Range constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StringUtils.isBlank(value))
        {
            return false;
        }

        //限制长度最大为11
        if (value.length() > DEFAULT_MAX)
        {
            String template = String.format(Message.CK_RANGE_MESSAGE_LENGTH_TYPE,value);
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(template).addConstraintViolation();
            return false;
        }

        //是否可数字化
        if (!StringUtils.isNumeric(value))
        {
            String template = String.format(Message.CK_NUMERIC_TYPE,value);
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(template).addConstraintViolation();
            return false;
        }
        long l = Long.parseLong(value);
        return l >= min && l <= max;
    }
}
