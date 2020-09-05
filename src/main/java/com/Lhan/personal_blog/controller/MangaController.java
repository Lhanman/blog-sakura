package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manga")
public class MangaController {

    @Autowired
    MangaService mangaService;

    @GetMapping("/getMangaList")
    public Result getMangaList()
    {
        try {
            return mangaService.getMangaListRedis("updateByKitSu");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Result.createWithErrorMessage(ErrorEnum.DATABASE_SQL_PARSE_ERROR);
        }
    }

}
