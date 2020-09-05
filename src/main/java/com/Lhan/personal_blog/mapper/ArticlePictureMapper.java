package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.ArticlePicture;
import com.Lhan.personal_blog.pojo.ArticlePictureExample;
import java.util.List;

public interface ArticlePictureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticlePicture record);

    int insertSelective(ArticlePicture record);

    List<ArticlePicture> selectByExample(ArticlePictureExample example);

    ArticlePicture selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticlePicture record);

    int updateByPrimaryKey(ArticlePicture record);

    int deleteArticlePictureByArticleId(Long article_id);

    int findPictureNum();
}