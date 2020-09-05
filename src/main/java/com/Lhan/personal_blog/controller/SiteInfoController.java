package com.Lhan.personal_blog.controller;


import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.SiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/stat")
public class SiteInfoController {

    @Autowired
    SiteInfoService siteInfoService;

    @RequestMapping("/v1/info")
    public Result getSiteStatInfo(HttpServletRequest request)
    {
        return siteInfoService.getSiteInfoAndUserAgent(request);
    }
}
