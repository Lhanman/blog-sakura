package com.Lhan.personal_blog.common.base;

import com.Lhan.personal_blog.constant.CommonConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PageInfo implements Serializable {
    private static final long serialVersionUID = -5172532647273106745L;

    private Integer page = CommonConstant.DEFAULT_PAGE_INDEX;

    private Integer size = CommonConstant.DEFAULT_PAGE_SIZE;

    private Integer total;
}
