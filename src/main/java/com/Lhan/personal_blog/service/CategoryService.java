package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.ArticleCategory;
import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    void addCategoryInfo(CategoryInfo categoryInfo);

    void addArticleCategory(ArticleCategory articleCategory);

    void deleteCategoryById(Long id);

    void updateCategory(CategoryInfo categoryInfo);

    CategoryInfo getOneById(Long id);

    List<CategoryInfo> listAllCategory();

    DataMap findCategoriesName();

    CategoryInfo findCategoryByName(String name);

    CategoryInfo findCategoryByArticleId(Long articleId);

    int findCategoryNum();

    DataMap findAllCategoryByPage(int pageNum,int pageSize);

    Result getCategoryList();



}
