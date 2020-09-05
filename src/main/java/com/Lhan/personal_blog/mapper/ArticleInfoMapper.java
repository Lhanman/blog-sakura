package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.pojo.ArticleInfoExample;
import com.Lhan.personal_blog.vo.PostsVo;

import java.util.Date;
import java.util.List;

public interface ArticleInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    List<ArticleInfo> selectByExample(ArticleInfoExample example);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    int findArticleCount();

    List<PostsVo> selectArchiveTotalGroupTimeline();

    List<PostsVo> selectByArchiveDate(Date archiveDate);
}