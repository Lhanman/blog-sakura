package com.Lhan.personal_blog.config;

import com.Lhan.personal_blog.filter.JWTAuthorizationFilter;
import com.Lhan.personal_blog.service.security.CustomUserServiceImpl;
import com.Lhan.personal_blog.util.JwtUtil;
import com.Lhan.personal_blog.util.MD5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    UserDetailsService customUserService()
    {
        return new CustomUserServiceImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    JwtUtil jwtUtil()
    {
        return new JwtUtil();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserService())
                //用MD5加密
                .passwordEncoder(new PasswordEncoder() {
                    MD5Util md5Util = new MD5Util();
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return md5Util.encode((String) rawPassword);
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return encodedPassword.equals(md5Util.encode((String) rawPassword));
                    }
                });
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .exceptionHandling().accessDeniedHandler(new RestAuthenticationAccessDeniedHandler())
                .and()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/","/posts/**","/music/**","/menu/**","/tags/**","/category/**")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
//                .antMatchers("/editor").hasAnyRole("USER")
//                .antMatchers("/editor").hasAnyRole("FRIEND")
//                .antMatchers("/superAdmin").hasAnyRole("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin();

        http.addFilterBefore(new JWTAuthorizationFilter(authenticationManager(),jwtUtil(),(CustomUserServiceImpl) customUserService()), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        http.headers().cacheControl();
    }
}
