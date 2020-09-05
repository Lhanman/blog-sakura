package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticleContent;
import com.Lhan.personal_blog.pojo.ArticleContentExample;
import java.util.List;

public interface ArticleContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    List<ArticleContent> selectByExample(ArticleContentExample example);

    ArticleContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);

    int deleteArticleContentByArticleId(Long article_id);
}