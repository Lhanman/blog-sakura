package com.Lhan.personal_blog.filter;

import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
                                    , HttpServletResponse response)throws AuthenticationException
    {
        //从输入流中获取到登录信息
        try {
            User user =  new User();
            user.setPhone(request.getParameter("phone"));
            user.setPassword(request.getParameter("password"));
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getPhone(),user.getPassword())
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 成功验证后调用的方法
     * 若成功则生成token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException
    {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        System.out.println("jwtUser: "+jwtUser.toString());

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities)
        {
            role = authority.getAuthority();
        }

        String token = JwtUtil.createToken(jwtUser.getUsername(),role);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String tokenStr = CommonConstant.TOKEN_PREFIX + token;
        response.setHeader("token",tokenStr);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException,ServletException
    {
        response.getWriter().write("authentication failed,reason:" + failed.getMessage());
    }

}
