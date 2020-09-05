package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserController extends BaseController{

    /**
     * 个人资料
     *      --更换头像
     */
    @PostMapping(value = "/uploadHead")
    @PermissionCheck("ROLE_USER")
    @CrossOrigin(origins = "http://lhanman.cn")
    public Result uploadHead(@AuthenticationPrincipal Principal principal, MultipartFile file)
    {
        String username = principal.getName();
        Long user_id = userService.findUserIdByUsername(username);
        if (file == null)
        {
            throw new RuntimeException("文件为空");
        }
        DataMap data = userService.updateAvatarImgByUserId(user_id,file);
        return Result.createWithModel(data.getData());
    }


    /**
     * 个人资料
     *      --显示个人信息
     */
    @PostMapping(value = "/getUserPersonalInfo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public Result getUserPersonalInfo(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        User user = userService.getUserPersonalInfoByUsername(username);
        user.setPhone(user.getPhone().substring(0,3)+"*****"+user.getPhone().substring(7));
        return Result.createWithModel(user);
    }

    /**
     * 个人信息
     *      --保存个人信息
     */
    @PostMapping(value = "/savePersonalData",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public Result savePersonalData(User user,@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        Long user_id = userService.findUserIdByUsername(username);
        user.setId(user_id);
        return userService.savePersonalData(user,username);
    }

    /**
     * 安全设置
     *      --判断手机号是否为用户
     */
    @PostMapping(value = "/findPhoneIsExist")
    public Result findPhoneIsExist(@RequestParam("phone") String phone)
    {
       return userService.findUserExistByPhone(phone);
    }

    /**
     * 评论管理
     *      --获得用户评论
     *
     */
    @GetMapping(value = "/getUserComment",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public Result getUserComment(@RequestParam("pageNum") String pageNum,
                                 @RequestParam("pageSize") String pageSize,
                                 @AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        return commentService.getUserComment(Integer.parseInt(pageNum),Integer.parseInt(pageSize),username);
    }

    /**
     * 已读一条消息
     */
    @GetMapping(value = "/readThisMsg",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String readThisMsg(@RequestParam("id") Long id,
                              @AuthenticationPrincipal Principal principal)
    {

        DataMap data = commentService.readOneComment(id);
        redisService.readOneCommentOnRedis(userService.findUserIdByUsername(principal.getName()));
        return JsonResult.build(data).toJSON();

    }

    /**
     * 已读所有消息
     */
    @GetMapping(value = "/readAllMsg",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String readAllMsg(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        DataMap data = commentService.readAllComment(username);
        redisService.readAllCommentOnRedis(userService.findUserIdByUsername(username));
        return JsonResult.build(data).toJSON();

    }

    /**
     * 获得用户未读消息
     */
    @GetMapping(value = "/getUserNews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public Result getUserNews(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
//        DataMap data = commentService.getUserNews(username);
        //用redis来取出数据
        DataMap data = redisService.getUserNews(username);
        return Result.createWithModel(data.getData());
    }
}
