package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.service.AuthService;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.service.security.CustomUserServiceImpl;
import com.Lhan.personal_blog.util.JwtUtil;
import com.Lhan.personal_blog.vo.AuthUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;


    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    UserDetailsService customUserService()
    {
        return new CustomUserServiceImpl();
    }


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    @Override
    public JwtUser register(JwtUser jwtUser) {
        final String username = jwtUser.getUsername();
        if (userService.findUserIdByUsername(username) != null)
        {
            return null;
        }

        return null;
    }

    @Override
    public Result login(String phone, String password) {

        //验证用户是否被封号
        if (userService.findUserByPhone(phone).getLocked())
        {
            return Result.createWithErrorMessage(ErrorEnum.LOGIN_DISABLE);
        }

        //用户验证
        final Authentication authentication = authentication(phone,password);
        if (userService.findUserByPhone(phone) == null)
        {
            return  Result.createWithErrorMessage(ErrorEnum.LOGIN_ERROR);
        }
        String username = userService.findUserByPhone(phone).getUsername();
        if (authentication == null)
        {
            return  Result.createWithErrorMessage(ErrorEnum.LOGIN_ERROR);
        }
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateAccessToken(jwtUser);
        //存储token
        jwtTokenUtil.putToken(username,token);

        AuthUserVo authUserVo = new AuthUserVo();
        authUserVo.setName(username);
        authUserVo.setToken(token);
        authUserVo.setAvatar(jwtUser.getAvatar());
        return Result.createWithModel(authUserVo);
    }

    @Override
    public void logout(String username) {
        jwtTokenUtil.deleteToken(username);

    }

    @Override
    public AuthUserVo refresh(String oldToken) {
        return null;
    }

    @Override
    public JwtUser getUserByToken(String token) {
        return null;
    }

    @Override
    @Transactional
    /**
     * 删除所有缓存
     */
    public void refreshCache() {
        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            redisTemplate.delete(iterator.next());
        }
    }


    private Authentication authentication(String username, String password)
    {
        try
        {
            /**
             * 该方法会调用userDetailsService.loadUserByUsername()去验证用户和密码，如果正确，则存储该用户到
             *  "security的context中
             */
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (Exception e)
        {
           return null;
        }
    }
}
