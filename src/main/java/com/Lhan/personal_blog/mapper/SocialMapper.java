package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Social;
import com.Lhan.personal_blog.pojo.SocialExample;
import java.util.List;

public interface SocialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Social record);

    int insertSelective(Social record);

    List<Social> selectByExample(SocialExample example);

    Social selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Social record);

    int updateByPrimaryKey(Social record);
}