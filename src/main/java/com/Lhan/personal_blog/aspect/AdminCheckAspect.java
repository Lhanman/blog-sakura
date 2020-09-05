package com.Lhan.personal_blog.aspect;

import com.Lhan.personal_blog.aspect.annotation.AdminCheck;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.util.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class AdminCheckAspect {
    public static final String ANONYMOUS_USER = "anonymousUser";

    @Pointcut("execution(public * com.Lhan.personal_blog.controller..*(..))")
    public void login()
    {

    }

    @Around("login() && @annotation(adminCheck)")
    public Object principalAround(ProceedingJoinPoint pjp, AdminCheck adminCheck)throws Throwable
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginName = auth.getName();


        //没有登陆
        if (loginName.equals(ANONYMOUS_USER))
        {
            return JsonResult.fail(CodeType.USER_NOT_LOGIN).toJSON();
        }
        //接口权限拦截
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String value = adminCheck.value();
        for (GrantedAuthority grantedAuthority : authorities)
        {
            if (grantedAuthority.getAuthority().equals(value))
            {
                return pjp.proceed();
            }
        }
        return JsonResult.fail(CodeType.PERMISSION_VERIFY_FAIL).toJSON();
    }
}
