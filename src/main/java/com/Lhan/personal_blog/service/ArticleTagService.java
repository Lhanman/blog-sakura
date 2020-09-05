package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.pojo.ArticleTag;

import java.util.List;

public interface ArticleTagService {

    void addArticleTagBatch(List<ArticleTag> articleTagList);
}
