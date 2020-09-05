package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.common.validator.annotion.NotNull;
import com.Lhan.personal_blog.validator.InsertComment;
import com.Lhan.personal_blog.validator.QueryComment;
import com.Lhan.personal_blog.validator.QueryUserComment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class CommentVo extends BaseVo<CommentVo>{

    private Long answerId;

    @NotBlank(groups = InsertComment.class)
    private String content;

    private Long pid;

    private Integer status;

    @NotNull(groups = {InsertComment.class, QueryComment.class})
    private Long postsId;

    private String authorName;

    private String authorAvatar;

    private String respondent;

    private String parentUserName;

    private Long createTime;

    private List<CommentVo> replies;

    private Long parentId;

    @NotNull(groups = {QueryUserComment.class})
    private String postsTitle;

    private Integer msgIsNotReadNum;

    private Boolean isRead;

    private String browser_name;

    private String browser_icon;

    private String browser_version;

    private String operation_icon;

    private String operating_system;

    private String city;

    private String area;



}
