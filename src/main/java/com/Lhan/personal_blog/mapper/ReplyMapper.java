package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Reply;
import com.Lhan.personal_blog.pojo.ReplyExample;
import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reply record);

    int insertSelective(Reply record);

    List<Reply> selectByExample(ReplyExample example);

    Reply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKey(Reply record);
}