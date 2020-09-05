package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.mapper.*;
import com.Lhan.personal_blog.pojo.ArticlePicture;
import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.service.*;
import com.Lhan.personal_blog.service.impl.RedisService;
import com.Lhan.personal_blog.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class BaseController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    @Autowired
    MessageService messageService;


    @Autowired
    ArticleTagService articleTagService;

    @Autowired
    UserService userService;


    @Autowired
    TagService tagService;

    @Autowired
    PictureService pictureService;

    @Resource
    CommentMapper commentMapper;

    @Resource
    ArticleTagMapper articleTagMapper;

    @Resource
    CategoryInfoMapper categoryInfoMapper;

    @Resource
    ArticlePictureMapper articlePictureMapper;

    @Resource
    MessageMapper messageMapper;

    @Autowired
    MailService mailService;

    @Autowired
    AdminIndexService adminIndexService;

    @Resource
    ArticleContentMapper articleContentMapper;

    @Resource
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    BookService bookService;

    @Autowired
    FriendshipLinkService friendshipLinkService;

    @Autowired
    MangaService mangaService;

    @Autowired
    AnimeService animeService;

    @Autowired
    MusicService musicService;

    @Autowired
    AuthService authService;
}
