package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.pojo.CategoryInfoExample;
import java.util.List;

public interface CategoryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CategoryInfo record);

    int insertSelective(CategoryInfo record);

    List<CategoryInfo> selectByExample(CategoryInfoExample example);

    CategoryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CategoryInfo record);

    int updateByPrimaryKey(CategoryInfo record);

    List<String> findCategoryNames();

    int findCategoryNum();
}