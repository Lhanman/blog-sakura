package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.util.DataMap;

public interface AdminIndexService {

    /**
     * 显示博客，用户，标签数量
     */
    DataMap findNumForIndex();
}
