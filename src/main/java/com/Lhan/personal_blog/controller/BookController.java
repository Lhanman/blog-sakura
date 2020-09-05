package com.Lhan.personal_blog.controller;


import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.service.BookService;
import com.Lhan.personal_blog.validator.QueryBook;
import com.Lhan.personal_blog.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/getBookList")
    public Result getBookList(@Validated({QueryBook.class}) BookVo bookVo)
    {
        if (bookVo.getProgress() == null)
        {
            return Result.createWithErrorMessage(ErrorEnum.PARAM_INCORRECT);
        }
        return bookService.getBookListByProgress(bookVo.getProgress());
    }

}
