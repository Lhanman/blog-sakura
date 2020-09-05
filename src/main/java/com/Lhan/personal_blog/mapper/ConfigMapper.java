package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Config;
import com.Lhan.personal_blog.pojo.ConfigExample;
import java.util.List;

public interface ConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    List<Config> selectByExample(ConfigExample example);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);


}