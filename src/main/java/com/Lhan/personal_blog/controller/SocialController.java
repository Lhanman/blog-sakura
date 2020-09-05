package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.SocialService;
import com.Lhan.personal_blog.vo.SocialVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SocialController {

    @Autowired
    SocialService socialService;


    @GetMapping("/social/v1/info")
    public Result getSocialInfo()
    {
        return socialService.getSocialInfo();
    }

    @GetMapping("/social/v1/socials")
    public Result getSocialEnableList(SocialVo socialVo)
    {
        socialVo.setIsEnabled(1);
        return socialService.getSocialList(socialVo);
    }

}
