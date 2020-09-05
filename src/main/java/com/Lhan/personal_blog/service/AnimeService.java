package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.entity.BiliBiliResult;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;

public interface AnimeService {

    Result getAnimeListByPage(String pageNum, String pageSize)throws Exception;

    void refreshAnimeCache();
}
