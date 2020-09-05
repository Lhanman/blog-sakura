package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.component.JavaScriptCheck;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.constant.SiteOwner;
import com.Lhan.personal_blog.pojo.Comment;
import com.Lhan.personal_blog.util.*;
import com.Lhan.personal_blog.validator.InsertComment;
import com.Lhan.personal_blog.validator.QueryComment;
import com.Lhan.personal_blog.vo.CommentVo;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * 评论和回复功能
 *
 */
@RestController
public class CommentController extends BaseController{


    /**
     * 获取该文章所有评论
     *
     */
    @GetMapping(value = "article/comment/getAllComment",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllComment(@RequestParam("articleId") String articleId)
    {
        DataMap data = commentService.findCommentByArticleId(Long.parseLong(articleId));
        return JsonResult.build(data).toJSON();
    }

    /**
     * 评论
     *
     */

    @PostMapping(value = "article/comment/publishComment",produces = MediaType.APPLICATION_JSON_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishComment(@RequestParam("commentContent") String commentContent,
                                 @RequestParam("articleId") String articleId,
                                 HttpServletRequest request,
                                 @AuthenticationPrincipal Principal principal)
    {

        String publish = principal.getName();
        Long userId = userService.findUserIdByUsername(publish);
        Comment comment = new Comment();
        comment.setIp(IpUtil.getIp(request));
        comment.setPid(Long.parseLong("0"));
        comment.setContent(JavaScriptCheck.javaScriptCheck(commentContent));
        comment.setCommentDate(DateFormatUtil.getDateStringForComment());
        comment.setAnswerId(userId);
        comment.setRespondentId(userService.findUserIdByUsername(SiteOwner.SITE_OWNER));
        comment.setIsEffective(true);
        commentService.addComment(comment,Long.parseLong(articleId));

        DataMap data = commentService.findCommentByArticleId(Long.parseLong(articleId));
        return JsonResult.build(data).toJSON();
    }

    @PostMapping(value = "article/comment/publishReply",produces = MediaType.APPLICATION_JSON_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishReply(@RequestParam("replyContent") String replyContent,
                               @RequestParam("pId") String pId,
                               @RequestParam("respondent") String respondent,
                               @RequestParam("articleId") String articleId,
                               HttpServletRequest request,
                               @AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();

        Comment comment = new Comment();
        comment.setIp(IpUtil.getIp(request));
        comment.setAnswerId(userService.findUserIdByUsername(username));
        comment.setRespondentId(userService.findUserIdByUsername(respondent));
        comment.setCommentDate(DateFormatUtil.getDateStringForComment());
        comment.setPid(Long.parseLong(pId.substring(1)));
        comment.setIsEffective(true);
        comment.setIsRead(false);
        // 去掉评论中的@
        if ('@' == replyContent.charAt(0))
        {
            comment.setContent(replyContent.substring(respondent.length() + 1).trim());
        }
        else
        {
            comment.setContent(replyContent.trim());
        }

        // 判断用户输入内容是否为空字符
        if (StringUtil.BLANK.equals(comment.getContent()))
        {
            return JsonResult.fail(CodeType.COMMENT_BLANK).toJSON();
        }
        else
        {
            //防止xss攻击
            comment.setContent(JavaScriptCheck.javaScriptCheck(comment.getContent()));
            commentService.addComment(comment,Long.parseLong(articleId));
        }
        //回复之后的回显“回复信息”功能
        DataMap data = commentService.replyReplyReturn(comment,username,respondent);
        return JsonResult.build(data).toJSON();
    }

    /**
     * 检查是否登录
     *
     */
    @GetMapping(value = "/isLogin",produces = MediaType.APPLICATION_JSON_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String isLogin()
    {
        return JsonResult.success().toJSON();
    }

    /**
     * Sakura获取评论
     */
    @GetMapping("/comments/comments-posts/v1/list")
    public Result getPostsCommentsByPostsIdList(@Validated({QueryComment.class})CommentVo commentVo)
    {
        return commentService.getPostsCommentsByPostsIdList(commentVo);
    }

    /**
     * Sakura 评论功能
     */
    @PostMapping("/comments/comments/v1/add")
    @PermissionCheck(value = "ROLE_USER")
    public Result savePostsComment(@Validated({InsertComment.class}) @RequestBody CommentVo commentVo,
                                   @AuthenticationPrincipal Principal principal,
                                   HttpServletRequest request)
    {
        String author = principal.getName();
        Long author_id = userService.findUserIdByUsername(author);

        Comment comment = new Comment();
        comment.setAnswerId(author_id);
        comment.setIp(IpUtil.getIp(request));
        comment.setContent(JavaScriptCheck.javaScriptCheck(commentVo.getContent()));
        comment.setCommentDate(DateFormatUtil.getDateStringForComment());
        comment.setUserAgent(request.getHeader("User-Agent"));
        comment.setIsEffective(true);
        if (commentVo.getParentId() == null)
        {
            comment.setPid(0L);
            comment.setRespondentId(userService.findUserIdByUsername(SiteOwner.SITE_OWNER));
            commentService.addComment(comment,commentVo.getPostsId());
        }
        else
        {
            Comment parentComment = commentMapper.selectByPrimaryKey(commentVo.getParentId());
            if (parentComment == null)
            {
                return Result.createWithErrorMessage(ErrorEnum.DATA_NOT_EXIST);
            }
            comment.setRespondentId(userService.findUserIdByUsername(commentVo.getRespondent()));
            comment.setPid(commentVo.getParentId());
            commentService.addComment(comment,commentVo.getPostsId());
        }

        return Result.createWithSuccessMessage();
    }


}
