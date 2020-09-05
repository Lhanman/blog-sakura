package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.mapper.CategoryInfoMapper;
import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.service.ArticleService;
import com.Lhan.personal_blog.service.PostsService;
import com.Lhan.personal_blog.service.TagService;
import com.Lhan.personal_blog.util.PageUtil;
import com.Lhan.personal_blog.vo.PostsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @Resource
    CategoryInfoMapper categoryInfoMapper;

    @Override
    public Result<PostsVo> getPostsList(PostsVo postsVo) {
        postsVo = Optional.ofNullable(postsVo).orElse(new PostsVo());
        int pageNum = Optional.ofNullable(postsVo.getPage()).orElse(1);
        int pageSize = Optional.ofNullable(postsVo.getSize()).orElse(5);
        int article_count = articleService.findArticleCount()-6;
        if (StringUtils.isNotBlank(postsVo.getKeywords()))
        {
            postsVo.setKeywords("%" + postsVo.getKeywords() + "%");
        }
        if (StringUtils.isNotBlank(postsVo.getTitle()))
        {
            postsVo.setTitle("%" + postsVo.getTitle() + "%");
        }
        if (postsVo.getIsWeight() != null)
        {
            List<PostsVo> postsVoList = articleService.findHottestPostsByView(pageNum,pageSize);
            PageInfo pageInfo = PageUtil.initPageInfo(pageNum,pageSize,article_count);
            return Result.createWithPaging(postsVoList,pageInfo );
        }
        if (postsVo.getPostsTagsId() != null)
        {
            String tag_name = tagService.findTagNameByTag_id(Long.parseLong(postsVo.getPostsTagsId()));
            List<PostsVo> postsVoList = articleService.findArticleByTag(tag_name,pageSize,pageNum);
            int count = tagService.getPostQuantityByTagName(tag_name);
            PageInfo pageInfo = PageUtil.initPageInfo(pageNum,pageSize,count);
            return Result.createWithPaging(postsVoList,pageInfo);
        }

        if(postsVo.getCategoryId() != null)
        {
            List<PostsVo> postsVoList = articleService.findPostsByCategoryId(postsVo.getCategoryId(),pageNum,pageSize);
            int count = categoryInfoMapper.selectByPrimaryKey(postsVo.getCategoryId()).getNumber();
            PageInfo pageInfo = PageUtil.initPageInfo(pageNum,pageSize,count);
            return Result.createWithPaging(postsVoList,pageInfo);
        }

        List<PostsVo> postsVoList = articleService.findPostsByPage(pageNum,pageSize);

        PageInfo pageInfo = PageUtil.initPageInfo(pageNum,pageSize,article_count);
        return Result.createWithPaging(postsVoList,pageInfo);
    }

    @Override
    public Result<PostsVo> getPosts(Long id) {
        PostsVo postsVo = articleService.findPostsById(id);
        if (postsVo == null)
        {
            return Result.createWithErrorMessage(ErrorEnum.PARAM_INCORRECT);
        }
        //之所以放在外面是防止走缓存访问文章时能过增加热度
        articleService.addTrafficByArticleId(id);
        return Result.createWithModel(postsVo);
    }

    @Override
    public Result<PostsVo> getArchiveTotalByTimeline(PostsVo postsVo) {
        List<PostsVo> postsVoList = articleService.findAllPostsOrderByTime();
        return Result.createWithModels(postsVoList);
    }


}
