package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Role;
import com.Lhan.personal_blog.pojo.RoleExample;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}