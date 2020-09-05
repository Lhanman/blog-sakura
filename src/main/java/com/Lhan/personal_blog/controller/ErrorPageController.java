package com.Lhan.personal_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/404")
    public String error404()
    {
        return "404";
    }

    @GetMapping("/403")
    public String error403()
    {
        return "403";
    }
}
