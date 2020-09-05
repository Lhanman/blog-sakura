package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.Book;
import com.Lhan.personal_blog.pojo.BookExample;
import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Book record);

    int insertSelective(Book record);

    List<Book> selectByExample(BookExample example);

    Book selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}