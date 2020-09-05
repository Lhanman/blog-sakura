package com.Lhan.personal_blog.vo;


import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.validator.InsertBook;
import com.Lhan.personal_blog.validator.QueryBook;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BookVo extends BaseVo<BookVo> implements Serializable {

    private static final long serialVersionUID = -5172532647273106746L;

    private Long id;

    @NotBlank(groups = {InsertBook.class})
    private String name;

    @NotBlank(groups = {QueryBook.class, InsertBook.class})
    private String progress;

    @NotBlank(groups = {InsertBook.class})
    private String description;

    @NotBlank(groups = {InsertBook.class})
    private String douban_link;

    private String purchase_link;

    private String pdf_link;

    private String picture_url;

    private String category;

    private String author;


}
