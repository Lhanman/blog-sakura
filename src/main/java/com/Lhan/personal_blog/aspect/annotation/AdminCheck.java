package com.Lhan.personal_blog.aspect.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdminCheck {
    String value();
}
