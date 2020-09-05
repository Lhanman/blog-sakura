package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.mapper.BookMapper;
import com.Lhan.personal_blog.pojo.Book;
import com.Lhan.personal_blog.pojo.BookExample;
import com.Lhan.personal_blog.service.BookService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.BookVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;

    @Override
    @Cacheable(value = "books")
    public Result getBookListByProgress(String progress) {
        BookExample example = new BookExample();
        example.or().andProgressEqualTo(progress);
        List<Book> bookList = bookMapper.selectByExample(example);
        List<BookVo> bookVoList = new ArrayList<>();

        bookList.forEach(book -> {
            bookVoList.add(new BookVo().setName(book.getName())
                                        .setDescription(book.getDescription())
                                        .setId(book.getId())
                                        .setDouban_link(book.getDoubanLink())
                                        .setPurchase_link(book.getPurchaseLink())
                                        .setPdf_link(book.getPdfLink())
                                        .setPicture_url(book.getPictureUrl())
                                        .setProgress(book.getProgress())
                                        .setCategory(book.getCategory())
                                        .setAuthor(book.getAuthor()));
        });
        return Result.createWithModels(bookVoList);
    }

    @Override
    public DataMap getBookListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<BookVo> bookVoList = new ArrayList<>();

        BookExample example = new BookExample();
        example.or();
        List<Book> bookList = bookMapper.selectByExample(example);
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);

        bookList.forEach(book -> {
            bookVoList.add(new BookVo().setId(book.getId())
                                        .setName(book.getName())
                                        .setDescription(book.getDescription())
                                        .setDouban_link(book.getDoubanLink())
                                        .setPurchase_link(book.getPurchaseLink())
                                        .setPdf_link(book.getPdfLink())
                                        .setProgress(book.getProgress())
                                        .setAuthor(book.getAuthor()));
        });
        JSONArray bookJsonArray = JSONArray.fromObject(bookVoList);
        return DataMap.success().setData(bookJsonArray);
    }

    @Override
    public int findBookNum() {
        BookExample example = new BookExample();
        example.or();
        List<Book> bookList = bookMapper.selectByExample(example);
        return bookList.size();
    }

    @Override
    @Transactional
    public void addBook(BookVo bookVo) {
        Book book = new Book();
        book.setName(bookVo.getName());
        book.setAuthor(bookVo.getAuthor());
        book.setDescription(bookVo.getDescription());
        book.setProgress(bookVo.getProgress());
        book.setDoubanLink(bookVo.getDouban_link());
        book.setPurchaseLink(bookVo.getPurchase_link());
        book.setPdfLink(bookVo.getPdf_link());
        bookMapper.insert(book);
    }

    @Override
    public Book findBookById(Long book_id) {
        return bookMapper.selectByPrimaryKey(book_id);
    }

    @Override
    @Transactional
    public void updateBook(BookVo bookVo) {
        Book book = new Book();
        book.setId(bookVo.getId());
        book.setName(bookVo.getName());
        book.setAuthor(bookVo.getAuthor());
        book.setDescription(bookVo.getDescription());
        book.setProgress(bookVo.getProgress());
        book.setDoubanLink(bookVo.getDouban_link());
        book.setPurchaseLink(bookVo.getPurchase_link());
        book.setPdfLink(bookVo.getPdf_link());
        System.out.println(book);
        bookMapper.updateByPrimaryKey(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long book_id) {
        bookMapper.deleteByPrimaryKey(book_id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"books"},allEntries = true)
    public void refreshBookCache() {

    }
}
