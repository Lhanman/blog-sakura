package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.common.validator.group.Insert;
import com.Lhan.personal_blog.common.validator.group.Update;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryVo extends BaseVo<CategoryVo>{

    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 分类含有文章数
     */
    private int number;


    /**
     * 一个分类有多少文章
     */
    private int total;

    /**
     * 分类图标
     */
    private String icon;
}
