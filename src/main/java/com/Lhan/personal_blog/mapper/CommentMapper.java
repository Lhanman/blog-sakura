package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Comment;
import com.Lhan.personal_blog.pojo.CommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);


    List<Comment> findCommentByPid(@Param("pId") Long pId);

//    String findNameById(@Param("id") Long id);

    Integer commentCount();

    int findCountIsReadByRespondentId(@Param("respondentId")Long respondentId);

    int readCommentByRespondentId(Long respondentId);

    List<Comment> getUserCommentByRespondentId(Long respondentId);
}