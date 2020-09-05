package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.dto.ArticleContentDto;
import com.Lhan.personal_blog.dto.ArticleInfoDto;
import com.Lhan.personal_blog.pojo.ArticleCategory;
import com.Lhan.personal_blog.pojo.ArticleContent;
import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.pojo.ArticlePicture;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.PostsVo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ArticleService  {

    void addArticleInfo(ArticleInfo articleInfo);

    void addArticleContent(ArticleContent articleContent);

    void addArticlePicture(ArticlePicture articlePicture);

    Long getArticleLatestId();

    void deleteArticleById(Long id);

    void deleteArticleBatch(Long[] ids);

    void updateArticleInfo(ArticleInfo articleInfo);

    void updateArticleContent(ArticleContent articleContent);

    void updateArticleCategory(ArticleCategory articleCategory);

    ArticleContentDto getOneById(Long id);

    ArticlePicture getPictureByArticleId(Long id);

    public DataMap findArticleByCategoryId(Long category_id, int rows, int pageNum);

//    List<ArticleInfoDto> listGeekArticleForIndex();
//
//    List<ArticleInfoDto> listAnimeArticleForIndex();
//
//    List<ArticleInfoDto> listMovieArticleForIndex();
//
//    List<ArticleInfoDto> listMoodArticleForIndex();

    List<ArticleInfoDto> listArticleForIndex(String category_name);

    List<ArticleInfoDto> listAll();

    List<ArticleInfoDto> listByCategoryId(Long category_id);

    List<ArticleInfoDto> listLatest();

    Long getCategoryIdByName(String category_name);

    /**
     * 显示博客基础信息
     * @param article_id
     * @return
     */
    DataMap findArticleInfoById(Long article_id);

    /**
     * 显示博客内容
     * @param article_id
     * @return
     */
    DataMap findArticleById(Long article_id);

    ArticleInfo addTrafficByArticleId(Long article_id);

    List<PostsVo> findArticleByTag(String tag, int rows, int pageNum);

    /**
     * 更新lastArticleId或nextArticleId
     * @param lastOrNext
     * @param lastOrNextArticleId
     * @param articleId
     */
    void updateArticleLastOrNextId(String lastOrNext,long lastOrNextArticleId,long articleId);

    /**
     * 分页查找所有博客详细信息
     * @return
     */
    DataMap findAllArticleByPage(int  pageNum,int pageSize);

    /**
     * 查找博客数量
     */
    int findArticleCount();

    /**
     * 当登录超时返回草稿
     */
    DataMap getDraftArticle(ArticleInfoDto articleInfoDto,String content);

    JSONObject findArticleByIdWithRedis(Long article_id);

    List<PostsVo> findPostsByPage(int pageNum,int pageSize);

    List<PostsVo> findHottestPostsByView(int pageNum,int pageSize);

    PostsVo findPostsById(Long article_id);

    List<PostsVo> findAllPostsOrderByTime();

    List<PostsVo> findPostsByCategoryId(Long categoryId,int pageNum,int pageSize);

    void refreshBlogCache();
}
