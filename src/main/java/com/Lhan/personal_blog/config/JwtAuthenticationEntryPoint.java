package com.Lhan.personal_blog.config;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
                                , AuthenticationException e) throws IOException, ServletException {
        //验证为未登录状态会进入此方法,认证错误
        System.out.println("认证失败," + e.getMessage());
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        String body = Result.createWithErrorMessage(ErrorEnum.ACCESS_NO_PRIVILEGE).toString();
        out.write(body);
        out.flush();
    }
}
