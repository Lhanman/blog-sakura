package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ArticleController extends BaseController{

    /**
     * 通过article_id获得博客
     * @param articleId
     * @return
     */
    @GetMapping("/getArticleById")
    public String getArticleById(@RequestParam("articleId") String articleId)
    {
        //当浏览者点击文章时增加traffic阅读量
        ArticleInfo articleInfo = articleService.addTrafficByArticleId(Long.parseLong(articleId));
        JSONObject jsonObject = articleService.findArticleByIdWithRedis(Long.parseLong(articleId));

        jsonObject.put("articleTraffic",articleInfo.getTraffic());
        int commentNumber=commentService.findCommentNumByArticleId(Long.parseLong(articleId));
        jsonObject.put("commentNumber",commentNumber);
        DataMap data;
        if (jsonObject != null)
        {
           data = DataMap.success().setData(jsonObject);
           return JsonResult.build(data).toJSON();
        }
        else
        {
            data = DataMap.fail(CodeType.ARTICLE_NOT_EXIST);
            return JsonResult.build(data).toJSON();
        }
    }



}
