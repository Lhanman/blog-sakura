package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.mapper.ArticleTagMapper;
import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleTagServiceImpl  implements ArticleTagService {

    @Resource
    ArticleTagMapper articleTagMapper;

    @Override
    @Transactional
    public void addArticleTagBatch(List<ArticleTag> articleTagList) {
        for (ArticleTag articleTag : articleTagList)
        {
            articleTagMapper.insert(articleTag);
        }

    }

}
