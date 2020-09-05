package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.vo.SocialVo;

import java.util.List;

public interface SocialService {


    Result<SocialVo> getSocialInfo();

    Result<SocialVo> getSocialList(SocialVo socialVo);

    int getSocialCount();

    List<SocialVo> getSocialListByPage(PageInfo pageInfo);
}
