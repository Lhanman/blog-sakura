package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.dto.ArticleTagDto;
import com.Lhan.personal_blog.mapper.ArticleTagMapper;
import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.TagsVo;

import javax.annotation.Resource;
import java.util.List;

public interface TagService {

    DataMap findTagCloud();

    List<ArticleTagDto> findTagCloudForIndex();

    List<String> findTagsNameByArticleId(Long article_id);

    int tagNum();

    DataMap findAllTagByPage(int pageNum,int pageSize);

    void deleteTagByTag_id(ArticleTag articleTag);

    void insertTag(ArticleTag articleTag);

    void updateTag(ArticleTag articleTag);

    Result<TagsVo> getTagsAndArticleQuantityList(TagsVo tagsVo);

    int getPostQuantityByTagName(String tagName);

    String findTagNameByTag_id(Long tag_id);

}
