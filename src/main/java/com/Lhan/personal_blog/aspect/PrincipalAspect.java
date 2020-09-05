package com.Lhan.personal_blog.aspect;

import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
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
public class PrincipalAspect {
    public static final String ANONYMOUS_USER = "anonymousUser";

    @Pointcut("execution(public * com.Lhan.personal_blog.controller..*(..))")
    public void login()
    {

    }

    @Around("login() && @annotation(permissionCheck)")
    public Object principalAround(ProceedingJoinPoint pjp, PermissionCheck permissionCheck)throws Throwable
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginName = auth.getName();
//        System.out.println(loginName);

        //没有登陆
        if (loginName.equals(ANONYMOUS_USER))
        {
            return Result.createWithErrorMessage(ErrorEnum.NEED_TO_LOGIN);
        }
        //接口权限拦截
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String value = permissionCheck.value();
        for (GrantedAuthority grantedAuthority : authorities)
        {
            if (grantedAuthority.getAuthority().equals(value))
            {
                return pjp.proceed();
            }
        }
        return Result.createWithErrorMessage(ErrorEnum.ACCESS_NO_PRIVILEGE);
    }
}
