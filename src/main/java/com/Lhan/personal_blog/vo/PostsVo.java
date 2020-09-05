package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.validator.InsertPosts;
import com.Lhan.personal_blog.validator.UpdatePosts;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class PostsVo extends BaseVo<PostsVo>{

    /**
     * 文章标题
     */
    @NotBlank(groups = {InsertPosts.class, UpdatePosts.class})
    private String title;

    /**
     * 文章内容
     */
    @NotBlank(groups = {InsertPosts.class,UpdatePosts.class})
    private String content;

    /**
     * 封面图
     */
    private String thumbnail;

    /**
     * 评论数
     */
    private Integer comments;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 标签
     */
    private List<ArticleTag> tagsList;

    /**
     * 标签名
     */
    private String tagsName;

    /**
     * 标签ID
     */
    private String postsTagsId;

    /**
     * 文章分类名称
     */
    private String categoryName;

    /**
     * 分类id
     */
    private Long categoryId;


    private Integer isWeight;

    private Integer isComment;

    private Long lastArticleId;

    private String lastStatus;

    private String lastPicUrl;

    private String lastTitle;

    private String nextStatus;

    private Long nextArticleId;

    private String nextPicUrl;

    private String nextTitle;

    private Date archiveDate;

    /**
     * 用于归档时保存文章自身列表
     */
    private List<PostsVo> archivePosts;

    /**
     * 归档中某月含有文章数
     */
    private Integer articleTotal;







}
