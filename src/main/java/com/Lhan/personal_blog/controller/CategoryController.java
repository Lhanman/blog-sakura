package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.ArticleInfoDto;
import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import com.Lhan.personal_blog.vo.CategoryVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CategoryController extends BaseController {
    /**
     * 获取所有分类信息
     *
     */
    @GetMapping("category/list")
    public List<CategoryInfo> listAllCategoryInfo()
    {
        return categoryService.listAllCategory();
    }

    /**
     * 通过categoryId获得对应分类的所有文章
     *
     */

    @GetMapping(value = "/list/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String listArticleByCategoryId(@PathVariable Long id, HttpServletRequest request)
    {
        try {
            int rows = Integer.parseInt(request.getParameter("rows"));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            DataMap data = articleService.findArticleByCategoryId(id,rows,pageNum);
            return JsonResult.build(data).toJSON();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @GetMapping(value = "/category/category/v1/list")
    public Result getCategoryList()
    {
        return categoryService.getCategoryList();
    }


}
