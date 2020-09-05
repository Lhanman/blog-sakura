package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.pojo.UserExample;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserLikeUserName(String username);

    int findUserNum();
}