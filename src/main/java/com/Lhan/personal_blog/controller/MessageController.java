package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.pojo.Comment;
import com.Lhan.personal_blog.pojo.Message;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.util.IpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class MessageController extends BaseController{

    /**
     *
     *留言功能
     *
     * @return
     */
    @PostMapping("message/publishMessage")
    public String publishComment(@RequestBody Message message, HttpServletRequest request) {
        String ip = IpUtil.getIp(request);
        message.setIp(ip);
        message.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        message.setIsReplied(false);
        messageService.addMessage(message);
        return "success";
    }


}
