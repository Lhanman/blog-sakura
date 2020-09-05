package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.MenuService;
import com.Lhan.personal_blog.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/front/v1/list")
    public Result getFrontMenuList(MenuVo menuVo)
    {
        return menuService.getFrontMenuList(menuVo);
    }


}
