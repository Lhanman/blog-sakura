package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Manga;
import com.Lhan.personal_blog.pojo.MangaExample;
import java.util.List;

public interface MangaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Manga record);

    int insertSelective(Manga record);

    List<Manga> selectByExample(MangaExample example);

    Manga selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Manga record);

    int updateByPrimaryKey(Manga record);
}