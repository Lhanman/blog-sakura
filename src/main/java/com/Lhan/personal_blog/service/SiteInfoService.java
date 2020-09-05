package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;

import javax.servlet.http.HttpServletRequest;

public interface SiteInfoService {


    Result getSiteInfoAndUserAgent(HttpServletRequest request);
}
