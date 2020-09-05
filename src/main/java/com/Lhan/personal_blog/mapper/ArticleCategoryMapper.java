package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticleCategory;
import com.Lhan.personal_blog.pojo.ArticleCategoryExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    List<ArticleCategory> selectByExample(ArticleCategoryExample example);

    ArticleCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    int deleteArticleCategoryByArticleId(Long article_id);
}