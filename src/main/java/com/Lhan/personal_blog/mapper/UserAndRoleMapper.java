package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.UserAndRole;
import com.Lhan.personal_blog.pojo.UserAndRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAndRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAndRole record);

    int insertSelective(UserAndRole record);

    List<UserAndRole> selectByExample(UserAndRoleExample example);

    UserAndRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAndRole record);

    int updateByPrimaryKey(UserAndRole record);

    int downUserRight(@Param("user_id")Long user_id,@Param("role_id")Long role_id);
}