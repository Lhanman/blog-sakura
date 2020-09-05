package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.dto.CategoryDto;
import com.Lhan.personal_blog.mapper.ArticleCategoryMapper;
import com.Lhan.personal_blog.mapper.CategoryInfoMapper;
import com.Lhan.personal_blog.pojo.ArticleCategory;
import com.Lhan.personal_blog.pojo.ArticleCategoryExample;
import com.Lhan.personal_blog.pojo.CategoryInfo;
import com.Lhan.personal_blog.pojo.CategoryInfoExample;
import com.Lhan.personal_blog.service.CategoryService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.vo.CategoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryInfoMapper categoryInfoMapper;

    @Resource
    ArticleCategoryMapper articleCategoryMapper;

    @Override
    @Transactional
    public void addCategoryInfo(CategoryInfo categoryInfo) {
        categoryInfoMapper.insert(categoryInfo);
    }

    @Override
    @Transactional
    public void addArticleCategory(ArticleCategory articleCategory) {
        articleCategoryMapper.insert(articleCategory);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        categoryInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryInfo categoryInfo) {
        categoryInfoMapper.updateByPrimaryKeySelective(categoryInfo);
    }

    @Override
    public CategoryInfo getOneById(Long id) {
        return categoryInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryInfo> listAllCategory() {
        //无条件查询所有
        CategoryInfoExample example = new CategoryInfoExample();
        return categoryInfoMapper.selectByExample(example);

    }

    @Override
    public DataMap findCategoriesName() {
        List<String> categoryNames = categoryInfoMapper.findCategoryNames();
        return DataMap.success().setData(categoryNames);
    }

    @Override
    public CategoryInfo findCategoryByName(String name) {
        CategoryInfoExample example = new CategoryInfoExample();
        example.or().andNameEqualTo(name);
        return categoryInfoMapper.selectByExample(example).get(0);

    }

    @Override
    public CategoryInfo findCategoryByArticleId(Long articleId) {
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.or().andArticleIdEqualTo(articleId);
        ArticleCategory articleCategory = articleCategoryMapper.selectByExample(example).get(0);
        CategoryInfo categoryInfo = categoryInfoMapper.selectByPrimaryKey(articleCategory.getCategoryId());
        return categoryInfo;
    }

    @Override
    public int findCategoryNum() {
        return categoryInfoMapper.findCategoryNum();
    }

    @Override
    public DataMap findAllCategoryByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        CategoryInfoExample example = new CategoryInfoExample();
        example.or();
        List<CategoryInfo> categoryInfoList = categoryInfoMapper.selectByExample(example);
        PageInfo<CategoryInfo> pageInfo = new PageInfo<>(categoryInfoList);

        for (CategoryInfo categoryInfo : categoryInfoList)
        {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryInfo.getId());
            categoryDto.setName(categoryInfo.getName());
            categoryDto.setNumber(categoryInfo.getNumber());
            categoryDto.setCreateBy(DateFormatUtil.getDateString(categoryInfo.getCreateBy()));
            categoryDto.setModifiedBy(DateFormatUtil.getDateString(categoryInfo.getModifiedBy()));

            categoryDtoList.add(categoryDto);
        }

        JSONArray categoryJsonArray = JSONArray.fromObject(categoryDtoList);
        return DataMap.success().setData(categoryJsonArray);
    }

    @Override
    public Result getCategoryList() {
        CategoryInfoExample example = new CategoryInfoExample();
        example.or();
        List<CategoryInfo> categoryList = categoryInfoMapper.selectByExample(example);

        List<CategoryVo> categoryVoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(categoryList))
        {
            categoryList.forEach(categoryInfo -> {
                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setId(categoryInfo.getId());
                categoryVo.setName(categoryInfo.getName());
                categoryVo.setIcon(categoryInfo.getIcon());
                categoryVo.setTotal(categoryInfo.getNumber());
                categoryVoList.add(categoryVo);
            });
        }
        return Result.createWithModels(categoryVoList);
    }


}
