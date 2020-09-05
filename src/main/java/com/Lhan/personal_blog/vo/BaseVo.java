package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.Message;
import com.Lhan.personal_blog.common.validator.annotion.IntegerNotNull;
import com.Lhan.personal_blog.common.validator.annotion.NotNull;
import com.Lhan.personal_blog.common.validator.group.Page;
import com.Lhan.personal_blog.common.validator.group.Update;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseVo<T> {

    /**
     * 主键
     */
    @NotNull(message = Message.ID_NOT_NULL,groups = {Update.class})
    protected Long id;

    /**
     * 关键词搜索
     */
    protected String keywords;

    /**
     * 页数
     */
    @IntegerNotNull(message = Message.PAGE_NOT_NULL,groups = {Page.class})
    protected Integer page;

    /**
     * 每页大小
     */
    @IntegerNotNull(message = Message.SIZE_NOT_NULL,groups = {Page.class})
    protected Integer size;

    public Long getId()
    {
        return id;
    }

    public BaseVo<T> setId(Long id)
    {
        this.id = id;
        return this;
    }

    public Integer getSize()
    {
        return size;
    }

    public BaseVo<T> setSize(Integer size)
    {
        this.size = size > 20? 20 : size;
        return this;
    }

}
