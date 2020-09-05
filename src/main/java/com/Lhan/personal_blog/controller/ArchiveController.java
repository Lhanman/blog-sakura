package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.PostsService;
import com.Lhan.personal_blog.vo.PostsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/archive/v1/list")
    public Result<PostsVo> getArchiveTotalByTimeline(PostsVo postsVo)
    {

        return null;
    }
}
