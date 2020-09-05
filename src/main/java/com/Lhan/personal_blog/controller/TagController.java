package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.dto.ArticleTagDto;
import com.Lhan.personal_blog.service.TagService;
import com.Lhan.personal_blog.util.*;
import com.Lhan.personal_blog.vo.TagsVo;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController extends BaseController{


//    @PostMapping(value = "/getTagArticle",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String getTagArticle(@RequestParam("tag") String tag,
//                                    HttpServletRequest request)
//    {
//        if (tag.equals(StringUtil.BLANK))
//        {
//            return JsonResult.build(tagService.findTagCloud()).toJSON();
//        }
//        tag = TransformCodingUtil.unicodeToString(tag);
//        int rows = Integer.parseInt(request.getParameter("rows"));
//        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
//        DataMap data = articleService.findArticleByTag(tag,rows,pageNum);
//        return JsonResult.build(data).toJSON();
//    }
//
//    /**
//     * 给首页标签云标签信息
//     * @return
//     */
//    @GetMapping(value = "/getTagsNameForIndex",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String getTagsNameForIndex()
//    {
//        DataMap data = tagService.findTagCloud();
//        return JsonResult.build(data).toJSON();
//
//    }

    @GetMapping("/tags-article-quantity/v1/list")
    public Result getTagsAndArticleQuantityList(TagsVo tagsVo, BindingResult result)
    {
        ThrowableUtils.checkParamArgument(result);
        return tagService.getTagsAndArticleQuantityList(tagsVo);
    }


}
