package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.Book;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.BookVo;

public interface BookService {

    Result getBookListByProgress(String progress);

    DataMap getBookListByPage(int pageNum, int pageSize);

    int findBookNum();

    void addBook(BookVo bookVo);

    Book findBookById(Long book_id);

    void updateBook(BookVo bookVo);

    void deleteBook(Long book_id);

    void refreshBookCache();

}
