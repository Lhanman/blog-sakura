package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.dto.NumDto;
import com.Lhan.personal_blog.service.*;
import com.Lhan.personal_blog.util.DataMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminIndexServiceImpl implements AdminIndexService {

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    @Override
    public DataMap findNumForIndex() {
        int userNum = userService.findUserNum();
        int tagNum = tagService.tagNum();
        int articleNum = articleService.findArticleCount()-6;
        int commentNum = commentService.commentNum();

        NumDto numDto = new NumDto();

        numDto.setArticleNum(articleNum);
        numDto.setTagNum(tagNum);
        numDto.setUserNum(userNum);
        numDto.setCommentNum(commentNum);

        JSONObject jsonObject = JSONObject.fromObject(numDto);

        return DataMap.success().setData(jsonObject);
    }
}
