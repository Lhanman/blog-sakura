package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.vo.AuthUserVo;

public interface AuthService {

    /**
     * 注册用户
     */
    JwtUser register(JwtUser jwtUser);

    /**
     * 登录
     */
    Result login(String username, String password);

    /**
     * 登出
     */
    void logout(String username);

    /**
     * 刷新token
     */
    AuthUserVo refresh(String oldToken);

    /**
     * 根据token获取用户信息
     */
    JwtUser getUserByToken(String token);


    void refreshCache();
}
