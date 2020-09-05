package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import com.Lhan.personal_blog.service.AuthService;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.JsonResult;
import com.Lhan.personal_blog.util.MD5Util;
import com.Lhan.personal_blog.vo.AuthUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.regex.Pattern;

/**
 * 登录
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Autowired
    AuthService authService;

    @PostMapping(value = "/changePassword",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result changePassword(@RequestParam("phone") String phone,
                                 @RequestParam("authCode") String authCode,
                                 @RequestParam("newPassword") String newPassword)
    {

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        //判断手机号是否正确
        if(!Pattern.matches(regex,phone))
        {
            return Result.createWithErrorMessage(ErrorEnum.PHONE_NUMBER_ERROR);
        }

        String trueMsgCode = (String) stringRedisService.get(phone);

        //判断获得的手机号是否为发送验证码的手机号
        if (trueMsgCode == null)
        {
            return Result.createWithErrorMessage(ErrorEnum.AUTH_CODE_EXPIRE);
        }

        //判断验证码是否正确
        if (!authCode.equals(trueMsgCode))
        {
            return Result.createWithErrorMessage(ErrorEnum.AUTH_CODE_ERROR);
        }
        User user = userService.findUserByPhone(phone);
        if (user == null)
        {
            return Result.createWithErrorMessage(ErrorEnum.ACCOUNT_NOT_EXIST);
        }

        MD5Util md5Util = new MD5Util();
        String MD5Password = md5Util.encode(newPassword);
        userService.updatePasswordByPhone(phone,MD5Password);

        //修改密码后成功删除redis中的验证码
        stringRedisService.remove(phone);

        return Result.createWithSuccessMessage();

    }

    @PostMapping(value = "/login")
    public Result login(User user)
    {
        return authService.login(user.getPhone(),user.getPassword());
    }

    @GetMapping(value = "/logout")
    public Result logout(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        authService.logout(username);
        return Result.createWithSuccessMessage();
    }

}
