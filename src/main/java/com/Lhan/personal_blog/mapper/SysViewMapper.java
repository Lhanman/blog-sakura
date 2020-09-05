package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.SysView;
import com.Lhan.personal_blog.pojo.SysViewExample;
import java.util.List;

public interface SysViewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysView record);

    int insertSelective(SysView record);

    List<SysView> selectByExample(SysViewExample example);

    SysView selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysView record);

    int updateByPrimaryKey(SysView record);
}