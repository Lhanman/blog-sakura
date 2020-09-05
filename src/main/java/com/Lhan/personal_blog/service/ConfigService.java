package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;

public interface ConfigService {

    Result getConfigList(int type);

    Result getConfigBaseList();

}
