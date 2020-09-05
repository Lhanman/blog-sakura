package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.PrincipalAspect;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import com.Lhan.personal_blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class RegisterController {


    @Autowired
    UserService userService;

    @Autowired
    StringRedisServiceImpl stringRedisService;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result register(User user, HttpServletRequest request)
    {
        String authCode = request.getParameter("authCode");

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        //判断手机号是否正确
        if(!Pattern.matches(regex,user.getPhone()))
        {
            return Result.createWithErrorMessage(ErrorEnum.PHONE_NUMBER_ERROR);
        }
        //通过Redis取真正的验证码
        String trueMsgCode = (String) stringRedisService.get(user.getPhone());

        //判断验证码是否过期是否正确
        if (trueMsgCode == null)
        {
            return Result.createWithErrorMessage(ErrorEnum.AUTH_CODE_EXPIRE);
        }

        //判断验证码是否正确
        if (!authCode.equals(trueMsgCode))
        {
            return Result.createWithErrorMessage(ErrorEnum.AUTH_CODE_ERROR);
        }

        //判断用户名是否存在
        if (userService.usernameIsExist(user.getUsername())
                || user.getUsername().equals(PrincipalAspect.ANONYMOUS_USER))
        {
            return Result.createWithErrorMessage(ErrorEnum.ACCOUNT_EXIST);
        }

        //注册时对密码进行MD5加密
        MD5Util md5Util = new MD5Util();
        user.setPassword(md5Util.encode(user.getPassword()));

        //注册结果
        DataMap data = userService.insert(user);

        if (data.getCode() == 0)
        {
            //注册成功删除redis中的注册码
            stringRedisService.remove(user.getPhone());
        }
        return Result.createWithSuccessMessage();

    }
}
