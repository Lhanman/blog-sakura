package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.SysLog;
import com.Lhan.personal_blog.pojo.SysLogExample;
import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}