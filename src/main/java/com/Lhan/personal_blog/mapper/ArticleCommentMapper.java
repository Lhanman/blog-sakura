package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticleComment;
import com.Lhan.personal_blog.pojo.ArticleCommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    ArticleComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    int findCommentNumByArticleId(@Param("article_id") Long article_id);

    int deleteArticleCommentByArticleId(@Param("article_id") Long article_id);
}