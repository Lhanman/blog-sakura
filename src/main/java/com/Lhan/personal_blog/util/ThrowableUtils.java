package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.exception.ApiInvalidParamException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;

public class ThrowableUtils {

    /**
     * 检验参数正确,拼接字段名和值到错误信息
     */
    public static void checkParamArgument(BindingResult result)
    {
        if (result != null && result.hasErrors())
        {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors))
            {
                FieldError error = errors.get(0);
                String rejectedValue = Objects.toString(error.getRejectedValue(),"");
                String defMsg = error.getDefaultMessage();
                //排除类上面的注解提示
                if (rejectedValue.contains(CommonConstant.DELIMITER_TO))
                {
                    //自己去确定错误字段
                    builder.append(defMsg);
                }
                else
                {
                    if (CommonConstant.DELIMITER_COLON.contains(defMsg))
                    {
                        builder.append(error.getField()).append("  ").append(defMsg);
                    }
                    else
                    {
                        builder.append(error.getField()).append("  ").append(defMsg)
                                .append(":").append(rejectedValue);
                    }

                }
            }
            else
            {
                String msg = result.getAllErrors().get(0).getDefaultMessage();
                builder.append(msg);
            }
            throw new ApiInvalidParamException(builder.toString());
        }

    }
}
