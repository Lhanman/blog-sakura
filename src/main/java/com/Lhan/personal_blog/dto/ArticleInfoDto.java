package com.Lhan.personal_blog.dto;

import com.Lhan.personal_blog.pojo.ArticleInfo;

import java.util.Date;
import java.util.List;

/**
 * 前端与后端交互的类
 * 用于index.html显示文章title和summary以及图片
 *
 */
public class ArticleInfoDto {

//    //最新的三个极客博客
//    private List<ArticleInfo> GeekArticles;
//    //最新的三个番剧推荐博客
//    private List<ArticleInfo> AnimeArticles;
//    //最新的三个影评博客
//    private List<ArticleInfo> MovieArticles;
//    //最新的三个随想博客（即发心情的博客）
//    private List<ArticleInfo> MoodArticles;


    /**
     tbl_article_info基础字段

     */
    private Long id;

    private String title;

    private String summary;

    //文章阅读量
    private Integer traffic;

    private Date createBy;

    private Integer likes;

    /**
     * tbl_article_picture基础字段
     *
     */
    private Long articlePictureId;

    private String pictureUrl;

    /**
     * tbl_article_tag基础字段
     *
     */

    private List<String> articleTagNames;

    private String stringTagsName;

    /**
     * tbl_category_info基础字段
     */
    private String categoryName;


    /**
     * tbl_article_comment基础字段
     */
    private int commentNum;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Long getArticlePictureId() {
        return articlePictureId;
    }

    public void setArticlePictureId(Long articlePictureId) {
        this.articlePictureId = articlePictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }


    public List<String> getArticleTagNames() {
        return articleTagNames;
    }

    public void setArticleTagNames(List<String> articleTagNames) {
        this.articleTagNames = articleTagNames;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getStringTagsName() {
        return stringTagsName;
    }

    public void setStringTagsName(String stringTagsName) {
        this.stringTagsName = stringTagsName;
    }
}
