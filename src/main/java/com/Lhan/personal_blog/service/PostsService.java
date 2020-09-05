package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.vo.PostsVo;

public interface PostsService {

    Result<PostsVo> getPostsList(PostsVo postsVo);

    Result<PostsVo> getPosts(Long id);

    Result<PostsVo> getArchiveTotalByTimeline(PostsVo postsVo);

}
