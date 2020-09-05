package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.entity.BiliBiliResult;
import com.Lhan.personal_blog.service.AnimeService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import com.Lhan.personal_blog.util.JsonUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    AnimeService animeService;


    @RequestMapping("/getAnime")
    public Result getAnimeByPage(HttpServletRequest request)
    {
        try
        {
            String pageNum = request.getParameter("pageNum");
            return animeService.getAnimeListByPage(pageNum, "16");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Result.createWithErrorMessage(ErrorEnum.DATABASE_SQL_PARSE_ERROR);
        }
    }

}
