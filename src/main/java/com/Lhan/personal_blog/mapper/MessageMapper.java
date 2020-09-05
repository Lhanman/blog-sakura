package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Message;
import com.Lhan.personal_blog.pojo.MessageExample;
import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);


    int findMessageNum();
}