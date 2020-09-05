package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.FriendshipLinkService;
import com.Lhan.personal_blog.vo.FriendshipLinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class FriendshipLinkController {

    @Autowired
    FriendshipLinkService friendshipLinkService;

    @GetMapping("/link/v2/list")
    public Result getFriendshipLinkMap(FriendshipLinkVo friendshipLinkVo)
    {
        return friendshipLinkService.getFriendshipLinkMap(friendshipLinkVo);
    }


}
