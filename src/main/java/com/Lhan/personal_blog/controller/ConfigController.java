package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.ConfigService;
import com.Lhan.personal_blog.validator.UpdateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/config")
@Validated(UpdateConfig.class)
public class ConfigController {

    @Autowired
    ConfigService configService;

    @RequestMapping("/config-base/v1/list")
    public Result getConfigBaseList()
    {
        return configService.getConfigBaseList();
    }
}
