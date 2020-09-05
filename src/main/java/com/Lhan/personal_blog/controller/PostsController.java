package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.PostsService;
import com.Lhan.personal_blog.vo.PostsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 */
@RestController
@RequestMapping("/posts")
public class PostsController extends BaseController{

    @Autowired
    private PostsService postsService;

    @GetMapping("/posts/v1/list")
    public Result<PostsVo> getPostsList(PostsVo postsVo)
    {
        return postsService.getPostsList(postsVo);
    }

    @GetMapping("/weight/v1/list")
    public Result<PostsVo> getWeightList(PostsVo postsVo)
    {
        postsVo.setIsWeight(1);
        return postsService.getPostsList(postsVo);
    }

    @GetMapping("/posts/v1/{id}")
    public Result getPost(@PathVariable Long id)
    {
        return postsService.getPosts(id);
    }


    @GetMapping("/archive/v1/list")
    public Result<PostsVo> getArchiveToTotalByTimeline(PostsVo postsVo)
    {
        return postsService.getArchiveTotalByTimeline(postsVo);
    }
}
