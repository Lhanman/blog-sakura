package com.Lhan.personal_blog.filter;

import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.service.security.CustomUserServiceImpl;
import com.Lhan.personal_blog.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证成功,进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserServiceImpl customUserService;

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                  CustomUserServiceImpl customUserService)
    {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.customUserService = customUserService;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException
    {
        String tokenHeader = request.getHeader(CommonConstant.TOKEN_HEADER);

        //如果请求头没有Authorization信息则直接放行
        if (tokenHeader == null || tokenHeader.equals(""))
        {
            // 不按规范,不允许通过验证
            chain.doFilter(request, response);
            return;
        }
        String username = jwtUtil.getUsernameFromToken(tokenHeader);
        String phone = jwtUtil.getPhone(tokenHeader);

        if (jwtUtil.containToken(username,tokenHeader) && username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null)
        {

            JwtUser jwtUser = (JwtUser)customUserService.loadUserByUsername(phone);
            if (jwtUtil.validateToken(tokenHeader,jwtUser))
            {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(jwtUser,null,jwtUser.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request,response);
    }

}
