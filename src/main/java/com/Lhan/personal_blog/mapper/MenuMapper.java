package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Menu;
import com.Lhan.personal_blog.pojo.MenuExample;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findMenuListByParentId(Long parent_id);
}