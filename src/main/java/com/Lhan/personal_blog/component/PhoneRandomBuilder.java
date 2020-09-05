package com.Lhan.personal_blog.component;

import org.springframework.stereotype.Component;

/**
 * 手机验证码随机生成组件
 */
@Component
public class PhoneRandomBuilder {

    public static String randomBuilder()
    {
        String result = "";
        for (int i=0; i<4; i++)
        {
            result += Math.round(Math.random() * 9);
        }

        return result;
    }
}
