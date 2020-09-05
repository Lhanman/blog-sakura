package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.pojo.ArticleTagExample;
import java.util.List;

public interface ArticleTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleTag record);

    int insertSelective(ArticleTag record);

    List<ArticleTag> selectByExample(ArticleTagExample example);

    ArticleTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTag record);

    int updateByPrimaryKey(ArticleTag record);

    List<String> findTagNameDistinct();

    int deleteArticleTagByArticleId(Long article_id);

    int findTagNum();

    List<ArticleTag> findTagsDistinct();
}