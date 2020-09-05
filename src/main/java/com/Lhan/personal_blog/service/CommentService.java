package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.Comment;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResultAdmin;
import com.Lhan.personal_blog.vo.CommentVo;

public interface CommentService {

    void addComment(Comment comment,Long articleId);

    /**
     * 通过文章id获得文章所有的评论和回复
     * @param articleId
     * @return
     */
    DataMap findCommentByArticleId(Long articleId);

    /**
     * 返回评论中的回复
     * @param comment
     * @param respondent
     * @return
     */
    DataMap replyReplyReturn(Comment comment,String answerer,String respondent);

    int commentNum();

    int findCommentNumByArticleId(Long article_id);


    /**
     * 分页获取评论相关信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    DataMap findAllCommentByPage(int pageNum,int pageSize);

    /**
     * 通过username模糊查询评论
     *
     */
    JsonResultAdmin.JsonData findCommentLikeUserName(int pageNum, int pageSize, String username);

    /**
     * 删除评论
     *
     */

    void deleteCommentByComment_id(Long comment_id);

    /**
     * 获得用户被回复的评论
     */
    Result getUserComment(int pageNum,int pageSize,String username);

    /**
     * 已读一条评论
     */
    DataMap readOneComment(Long id);

    /**
     * 标记全部评论为已读
     */
    DataMap readAllComment(String username);

    /**
     * 获得用户未读消息数量
     */
    DataMap getUserNews(String username);


    /**
     * sakura 获取评论
     * @param commentVo
     * @return
     */
    Result getPostsCommentsByPostsIdList(CommentVo commentVo);

    /**
     * Sakura 发送评论
     */
    Result savePostsComment(CommentVo commentVo);
}
