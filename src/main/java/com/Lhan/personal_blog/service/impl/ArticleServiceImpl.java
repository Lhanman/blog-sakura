package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.ArticleContentDto;
import com.Lhan.personal_blog.dto.ArticleInfoDto;
import com.Lhan.personal_blog.mapper.*;
import com.Lhan.personal_blog.pojo.*;
import com.Lhan.personal_blog.service.*;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.vo.PostsVo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleInfoMapper articleInfoMapper;

    @Resource
    ArticlePictureMapper articlePictureMapper;

    @Resource
    ArticleCategoryMapper articleCategoryMapper;

    @Resource
    CategoryInfoMapper categoryInfoMapper;

    @Resource
    ArticleContentMapper articleContentMapper;

    @Resource
    ArticleTagMapper articleTagMapper;

    @Resource
    CategoryService categoryService;

    @Resource
    CommentService commentService;

    @Resource
    ArticleCommentMapper articleCommentMapper;

    @Resource
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @Autowired
    PictureService pictureService;

    @Resource
    CommentMapper commentMapper;


    @Override
    @Transactional
    public void addArticleInfo(ArticleInfo articleInfo) {
        ArticleInfo endArticle = articleInfoMapper.selectByPrimaryKey(getArticleLatestId());
        //设置文章为的上一篇文章id并且设置上一篇文章的下一篇文章的id
        if (endArticle != null)
        {
            articleInfo.setLastarticleid(endArticle.getId());
        }
        articleInfoMapper.insert(articleInfo);
        if (endArticle != null)
        {
            ArticleInfo updateArticle = articleInfoMapper.selectByPrimaryKey(endArticle.getId());
            updateArticle.setNextarticleid(articleInfo.getId());
            articleInfoMapper.updateByPrimaryKeySelective(updateArticle);
        }

    }

    @Override
    @Transactional
    public void addArticleContent(ArticleContent articleContent) {
        articleContentMapper.insert(articleContent);
    }

    @Override
    @Transactional
    public void addArticlePicture(ArticlePicture articlePicture) {
        articlePictureMapper.insert(articlePicture);
    }

    @Override
    public Long getArticleLatestId() {
        ArticleInfoExample example = new ArticleInfoExample();
        example.setOrderByClause("id desc");
        return articleInfoMapper.selectByExample(example).get(0).getId();
    }


    @Override
    @Transactional
    public void deleteArticleById(Long id) {
        //删除tbl_article_info表,需将lastArticle_id和nextArticleId字段进行修改，相当于链表
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(id);
        ArticleInfo lastArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getLastarticleid());
        ArticleInfo nextArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getNextarticleid());
       if (lastArticle != null)
       {
           lastArticle.setNextarticleid(articleInfo.getNextarticleid());
           articleInfoMapper.updateByPrimaryKey(lastArticle);
       }
       if (nextArticle != null)
       {
           nextArticle.setLastarticleid(articleInfo.getLastarticleid());
           articleInfoMapper.updateByPrimaryKey(nextArticle);
       }
        articleInfoMapper.deleteByPrimaryKey(id);


        //删除tbl_article_content表
        articleContentMapper.deleteArticleContentByArticleId(id);

        //将tbl_category_info表中的number字段减一
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.or().andArticleIdEqualTo(id);
        Long category_id = articleCategoryMapper.selectByExample(example).get(0).getCategoryId();
        CategoryInfo categoryInfo = categoryInfoMapper.selectByPrimaryKey(category_id);
        categoryInfo.setNumber(categoryInfo.getNumber() - 1);
        categoryInfoMapper.updateByPrimaryKeySelective(categoryInfo);

        //删除tbl_article_category表
        articleCategoryMapper.deleteArticleCategoryByArticleId(id);


        //删除tbl_article_tag表
        articleTagMapper.deleteArticleTagByArticleId(id);

        //删除tbl_article_picture表,并删除对应图片
        ArticlePictureExample articlePictureExample = new ArticlePictureExample();
        articlePictureExample.or().andArticleIdEqualTo(id);
        ArticlePicture articlePicture = articlePictureMapper.selectByExample(articlePictureExample).get(0);
        pictureService.deletePictureByPictureId(articlePicture);
        articlePictureMapper.deleteArticlePictureByArticleId(id);

        //删除评论表(之后再开发)
        ArticleCommentExample example1 = new ArticleCommentExample();
        example1.or().andArticleIdEqualTo(id);
        List<ArticleComment> articleCommentList = articleCommentMapper.selectByExample(example1);
        for (ArticleComment articleComment : articleCommentList)
        {
           commentMapper.deleteByPrimaryKey(articleComment.getCommentId());
        }
        articleCommentMapper.deleteArticleCommentByArticleId(id);
    }

    /**
     * 风险大暂时不做
     * @param ids
     */
    @Override
    public void deleteArticleBatch(Long[] ids) {
        //删除tbl_article_info表


    }

    @Override
    @Transactional
    @CacheEvict(value = "article",key = "#articleInfo.id")
    public void updateArticleInfo(ArticleInfo articleInfo) {
        articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
    }

    @Override
    @Transactional
    public void updateArticleContent(ArticleContent articleContent) {
        articleContentMapper.updateByPrimaryKeySelective(articleContent);
    }

    @Override
    @Transactional
    public void updateArticleCategory(ArticleCategory articleCategory) {
        articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
    }

    @Override
    public ArticleContentDto getOneById(Long id) {
        return null;
    }

    @Override
    public ArticlePicture getPictureByArticleId(Long id) {
        ArticlePictureExample example = new ArticlePictureExample();
        example.or().andArticleIdEqualTo(id);
        return articlePictureMapper.selectByExample(example).get(0);

    }

//    /**
//     * 返回最新的三个极客博客
//     * 有很严重的重复代码块
//     * 可以重构
//     * 但是我好懒啊啊啊啊啊啊啊！！！！
//     *
//     * @return
//     */
//    @Override
//    public List<ArticleInfoDto> listGeekArticleForIndex() {
//        Long category_id = getCategoryIdByName("极客");
//        ArticleCategoryExample example = new ArticleCategoryExample();
//        example.setOrderByClause("id desc");
//        example.or().andCategoryIdEqualTo(category_id);
//        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
//        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
//        if (articleCategories.size() >= 3)
//        {
//            for (int i=0; i<3; i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
////                System.out.println("============************"+articlePicture.getPictureUrl()+"*******"+article_id);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        else
//        {
//            for (int i=0; i<articleCategories.size(); i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        return articleInfoDtos;
//    }
//
//    /**
//     * 返回最新的三个番评博客
//     * 可以重构
//     * @return
//     */
//    @Override
//    public List<ArticleInfoDto> listAnimeArticleForIndex() {
//        Long category_id = getCategoryIdByName("番评");
//        ArticleCategoryExample example = new ArticleCategoryExample();
//        example.setOrderByClause("id desc");
//        example.or().andCategoryIdEqualTo(category_id);
//        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
//        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
//        if (articleCategories.size() >= 3)
//        {
//            for (int i=0; i<3; i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        else
//        {
//            for (int i=0; i<articleCategories.size(); i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        return articleInfoDtos;
//    }
//
//    /**
//     * 返回最新的五个影评博客
//     * 可重构
//     * @return
//     */
//    @Override
//    public List<ArticleInfoDto> listMovieArticleForIndex() {
//        Long category_id = getCategoryIdByName("影评");
//        ArticleCategoryExample example = new ArticleCategoryExample();
//        example.setOrderByClause("id desc");
//        example.or().andCategoryIdEqualTo(category_id);
//        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
//        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
//        if (articleCategories.size() >= 5)
//        {
//            for (int i=0; i<5; i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        else
//        {
//            for (int i=0; i<articleCategories.size(); i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        return articleInfoDtos;
//    }
//
//    /**
//     * 返回最新的三个随想博客
//     * 可重构
//     * 但是我懒啊啊啊啊啊啊啊啊啊！！！！
//     * 毕竟还是完成效果再优化代码结构好吧！！！
//     *
//     * @return
//     */
//    @Override
//    public List<ArticleInfoDto> listMoodArticleForIndex() {
//        Long category_id = getCategoryIdByName("随想");
//        ArticleCategoryExample example = new ArticleCategoryExample();
//        example.setOrderByClause("id desc");
//        example.or().andCategoryIdEqualTo(category_id);
//        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
//        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
//        if (articleCategories.size() >= 3)
//        {
//            for (int i=0; i<3; i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        else
//        {
//            for (int i=0; i<articleCategories.size(); i++)
//            {
//                Long article_id = articleCategories.get(i).getArticleId();
//                ArticleInfoDto articleInfoDto = new ArticleInfoDto();
//                //填充文章基础信息到ArticleInfoDto
//                ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
//                articleInfoDto.setId(articleInfo.getId());
//                articleInfoDto.setTitle(articleInfo.getTitle());
//                articleInfoDto.setSummary(articleInfo.getSummary());
//                articleInfoDto.setTraffic(articleInfo.getTraffic());
//                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
//                //填充文章图片信息
//                ArticlePictureExample example1 = new ArticlePictureExample();
//                example1.or().andArticleIdEqualTo(article_id);
//                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
//                articleInfoDto.setArticlePictureId(articlePicture.getId());
//                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
//                articleInfoDtos.add(articleInfoDto);
//            }
//        }
//        return articleInfoDtos;
//    }

    /**
     * 为index.html写的回显数据的方法的重构后的方法
     * 通过分类名回显不同的数据
     * 极客,番评,随想只需要三个数据
     * 而影评需要五个数据,进行区别对待.
     * @param category_name
     * @return
     */
    public List<ArticleInfoDto> listArticleForIndex(String category_name)
    {
        Long category_id = getCategoryIdByName(category_name);
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.setOrderByClause("id desc");
        example.or().andCategoryIdEqualTo(category_id);
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
        if (category_name.equals("影评"))
        {
            if (articleCategories.size() >= 5)
            {
                for (int i=0; i<5; i++)
                {
                    Long article_id = articleCategories.get(i).getArticleId();
                    ArticleInfoDto articleInfoDto = new ArticleInfoDto();
                    //填充文章基础信息到ArticleInfoDto
                    ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
                    articleInfoDto.setId(articleInfo.getId());
                    articleInfoDto.setTitle(articleInfo.getTitle());
                    articleInfoDto.setSummary(articleInfo.getSummary());
                    articleInfoDto.setTraffic(articleInfo.getTraffic());
                    articleInfoDto.setCreateBy(articleInfo.getCreateBy());

                    //填充文章图片信息
                    ArticlePictureExample example1 = new ArticlePictureExample();
                    example1.or().andArticleIdEqualTo(article_id);
                    if (articlePictureMapper.selectByExample(example1).isEmpty())
                    {
                        articleInfoDto.setPictureUrl("assets/images/ServerError.jpg");
                    }
                    else
                    {
                        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);

                        articleInfoDto.setArticlePictureId(articlePicture.getId());
                        articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
                    }

                    //填充文章评论信息
                    int commentNum = commentService.findCommentNumByArticleId(article_id);
                    articleInfoDto.setCommentNum(commentNum);

                    //填充文章标签信息
                    ArticleTagExample example2 = new ArticleTagExample();
                    example2.or().andArticleIdEqualTo(article_id);
                    List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
                    List<String> articleTagNames = new ArrayList<>();
                    if (articleTagList.isEmpty())
                    {
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    else
                    {
                        for (ArticleTag articleTag : articleTagList)
                        {
                            articleTagNames.add(articleTag.getTagName());
                        }
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    //将dto添加进dto集合中
                    articleInfoDtos.add(articleInfoDto);
                }
            }
            else
            {
                for (int i=0; i<articleCategories.size(); i++)
                {
                    Long article_id = articleCategories.get(i).getArticleId();
                    ArticleInfoDto articleInfoDto = new ArticleInfoDto();
                    //填充文章基础信息到ArticleInfoDto
                    ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
                    articleInfoDto.setId(articleInfo.getId());
                    articleInfoDto.setTitle(articleInfo.getTitle());
                    articleInfoDto.setSummary(articleInfo.getSummary());
                    articleInfoDto.setTraffic(articleInfo.getTraffic());
                    articleInfoDto.setCreateBy(articleInfo.getCreateBy());
                    //填充文章图片信息
                    ArticlePictureExample example1 = new ArticlePictureExample();
                    example1.or().andArticleIdEqualTo(article_id);
                    if (articlePictureMapper.selectByExample(example1).isEmpty())
                    {
                        articleInfoDto.setPictureUrl("assets/images/ServerError.jpg");
                    }
                    else {
                        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);

                        articleInfoDto.setArticlePictureId(articlePicture.getId());
                        articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
                        }
                    //填充文章评论信息
                    int commentNum = commentService.findCommentNumByArticleId(article_id);
                    articleInfoDto.setCommentNum(commentNum);

                    //填充文章标签信息
                    ArticleTagExample example2 = new ArticleTagExample();
                    example2.or().andArticleIdEqualTo(article_id);
                    List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
                    List<String> articleTagNames = new ArrayList<>();
                    if (articleTagList.isEmpty())
                    {
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    else
                    {
                        for (ArticleTag articleTag : articleTagList)
                        {
                            articleTagNames.add(articleTag.getTagName());
                        }
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    //将dto添加进dto集合中
                    articleInfoDtos.add(articleInfoDto);
                }
            }
            return articleInfoDtos;
        }
        else {
            if (articleCategories.size() >= 3)
            {
                for (int i=0; i<3; i++)
                {
                    Long article_id = articleCategories.get(i).getArticleId();
                    ArticleInfoDto articleInfoDto = new ArticleInfoDto();
                    //填充文章基础信息到ArticleInfoDto
                    ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
                    articleInfoDto.setId(articleInfo.getId());
                    articleInfoDto.setTitle(articleInfo.getTitle());
                    articleInfoDto.setSummary(articleInfo.getSummary());
                    articleInfoDto.setTraffic(articleInfo.getTraffic());
                    articleInfoDto.setCreateBy(articleInfo.getCreateBy());
                    //填充文章图片信息
                    ArticlePictureExample example1 = new ArticlePictureExample();
                    example1.or().andArticleIdEqualTo(article_id);
                    if (articlePictureMapper.selectByExample(example1).isEmpty())
                    {
                        articleInfoDto.setPictureUrl("assets/images/ServerError.jpg");

                    }
                    else
                    {
                        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
                        articleInfoDto.setArticlePictureId(articlePicture.getId());
                        articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
                    }


                    //填充文章评论信息
                    int commentNum = commentService.findCommentNumByArticleId(article_id);
                    articleInfoDto.setCommentNum(commentNum);

                    //填充文章标签信息
                    ArticleTagExample example2 = new ArticleTagExample();
                    example2.or().andArticleIdEqualTo(article_id);
                    List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);

                    List<String> articleTagNames = new ArrayList<>();
                    if (articleTagList.isEmpty())
                    {
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    else
                    {
                        for (ArticleTag articleTag : articleTagList)
                        {
                            articleTagNames.add(articleTag.getTagName());
                        }
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }

                    //将dto添加进dto集合中
                    articleInfoDtos.add(articleInfoDto);
                }
            }
            else
            {
                for (int i=0; i<articleCategories.size(); i++)
                {
                    Long article_id = articleCategories.get(i).getArticleId();
                    ArticleInfoDto articleInfoDto = new ArticleInfoDto();
                    //填充文章基础信息到ArticleInfoDto
                    ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
                    articleInfoDto.setId(articleInfo.getId());
                    articleInfoDto.setTitle(articleInfo.getTitle());
                    articleInfoDto.setSummary(articleInfo.getSummary());
                    articleInfoDto.setTraffic(articleInfo.getTraffic());
                    articleInfoDto.setCreateBy(articleInfo.getCreateBy());
                    //填充文章图片信息
                    ArticlePictureExample example1 = new ArticlePictureExample();
                    example1.or().andArticleIdEqualTo(article_id);

                    if (articlePictureMapper.selectByExample(example1).isEmpty())
                    {
                        articleInfoDto.setPictureUrl("assets/images/ServerError.jpg");
                    }
                    else
                    {
                        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
                        articleInfoDto.setArticlePictureId(articlePicture.getId());
                        articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
                    }


                    //填充文章评论信息
                    int commentNum = commentService.findCommentNumByArticleId(article_id);
                    articleInfoDto.setCommentNum(commentNum);

                    //填充文章标签信息
                    ArticleTagExample example2 = new ArticleTagExample();
                    example2.or().andArticleIdEqualTo(article_id);
                    List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
                    List<String> articleTagNames = new ArrayList<>();
                    if (articleTagList.isEmpty())
                    {
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    else
                    {
                        for (ArticleTag articleTag : articleTagList)
                        {
                            articleTagNames.add(articleTag.getTagName());
                        }
                        articleInfoDto.setArticleTagNames(articleTagNames);
                    }
                    //将dto添加进dto集合中
                    articleInfoDtos.add(articleInfoDto);
                }
            }
            return articleInfoDtos;
        }
        }

    @Override
    public List<ArticleInfoDto> listAll() {
        return null;
    }

    /**
     * 通过分类id返回该分类下的所有博客
     *
     * @param category_id
     * @return
     */
    @Override
    public List<ArticleInfoDto> listByCategoryId(Long category_id) {
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.or().andCategoryIdEqualTo(category_id);
        example.setOrderByClause("id desc");
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
        List<ArticleInfoDto> articleInfoDtos = new ArrayList<>();
        for (int i=0; i<articleCategories.size(); i++)
        {
            Long articleId = articleCategories.get(i).getArticleId();
            ArticleInfoDto articleInfoDto = new ArticleInfoDto();
            //填充博客基础信息
            ArticleInfoExample example1 = new ArticleInfoExample();
            example1.or().andIdEqualTo(articleId);
            ArticleInfo articleInfo = articleInfoMapper.selectByExample(example1).get(0);
            articleInfoDto.setId(articleInfo.getId());
            articleInfoDto.setTitle(articleInfo.getTitle());
            articleInfoDto.setSummary(articleInfo.getSummary());
            articleInfoDto.setCreateBy(articleInfo.getCreateBy());
            articleInfoDto.setTraffic(articleInfo.getTraffic());

            //填充博客图片信息
            ArticlePictureExample example2 = new ArticlePictureExample();
            example2.or().andArticleIdEqualTo(articleInfo.getId());
            ArticlePicture articlePicture = articlePictureMapper.selectByExample(example2).get(0);
            articleInfoDto.setArticlePictureId(articlePicture.getId());
            articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
            articleInfoDtos.add(articleInfoDto);
        }
        return articleInfoDtos;
    }

    /**
     * 用包装类JsonResult封装博客
     * 并设置分页功能
     *
     * @return
     */

    public DataMap findArticleByCategoryId(Long category_id,int rows,int pageNum)
    {
        PageInfo<ArticleCategory> pageInfo;
        PageHelper.startPage(pageNum,rows,"id desc");
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.or().andCategoryIdEqualTo(category_id);
        example.setOrderByClause("id desc");
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByExample(example);
//        System.out.println(articleCategories.size());
        List<ArticleInfoDto> articles = new ArrayList<>();
        List<Map<String,Object>> newArticles = new ArrayList<>();
        Map<String,Object> map;

        //填充ArticleInfoDto
        fillArticleInfoDto(articles,articleCategories,category_id);
        pageInfo = new PageInfo<>(articleCategories);

        for (ArticleInfoDto article : articles)
        {
            map = new HashMap<>();
            map.put("ArticleTitle",article.getTitle());
            map.put("ArticleSummary",article.getSummary());
            map.put("PictureUrl",article.getPictureUrl());
            map.put("ArticlePictureId",article.getArticlePictureId());
            map.put("CreateBy",DateFormatUtil.getDateString(article.getCreateBy()));
            map.put("Category",article.getCategoryName());
            map.put("Traffic",article.getTraffic());
            map.put("ArticleId",article.getId());
            map.put("articleTagNames",article.getArticleTagNames());
            map.put("traffic",article.getTraffic());
            map.put("commentNum",article.getCommentNum());
            newArticles.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(newArticles);
        Map<String,Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());

        jsonArray.add(thisPageInfo);
        return DataMap.success().setData(jsonArray);
    }



    @Override
    public List<ArticleInfoDto> listLatest() {
        return null;
    }

    /**
     * 通过分类名字获得分类id
     * @param category_name
     * @return
     */
    @Override
    public Long getCategoryIdByName(String category_name) {
        CategoryInfoExample example = new CategoryInfoExample();
        example.or().andNameEqualTo(category_name);
        CategoryInfo categoryInfo = categoryInfoMapper.selectByExample(example).get(0);
        return categoryInfo.getId();
    }

    @Override
    public DataMap findArticleInfoById(Long article_id) {
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("articleTitle",articleInfo.getTitle());
        dataMap.put("modifiedBy",DateFormatUtil.getDateString(articleInfo.getModifiedBy()));
        dataMap.put("author","Lhan");
        dataMap.put("id",articleInfo.getId());
        return DataMap.success().setData(dataMap);
    }

    /**
     * 显示博客内容功能
     *
     * @param article_id
     * @return
     */
    @Override

    public DataMap findArticleById(Long article_id) {

        //获取文章的相关对象
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
        ArticleContentExample example = new ArticleContentExample();
        example.or().andArticleIdEqualTo(article_id);
        ArticleContent articleContent = articleContentMapper.selectByExample(example).get(0);
        ArticlePictureExample example1 = new ArticlePictureExample();
        example1.or().andArticleIdEqualTo(article_id);
        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
        ArticleTagExample example2 = new ArticleTagExample();
        example2.or().andArticleIdEqualTo(article_id);
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);

        //分类信息
        CategoryInfo categoryInfo = categoryService.findCategoryByArticleId(article_id);
        //评论信息
        int commentNumber=commentService.findCommentNumByArticleId(article_id);

        //上一篇博客以及下一篇博客的信息
        ArticleInfo lastArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getLastarticleid());
        ArticleInfo nextArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getNextarticleid());

        List<String> articleTagsName = new ArrayList<>();
        for (ArticleTag articleTag : articleTagList)
        {
            articleTagsName.add(articleTag.getTagName());
        }
        //将文章添加到DataMap与前端交互(充当dto层)
        if (articleInfo!=null && articleContent!=null && articlePicture!=null && articleTagList!=null)
        {
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id",article_id);
            dataMap.put("articleTitle",articleInfo.getTitle());
            dataMap.put("publishDate",DateFormatUtil.getDateString(articleInfo.getCreateBy()));
            dataMap.put("articleContent",articleContent.getContent());
            dataMap.put("articleTags",articleTagList);
            dataMap.put("articleTagsName",articleTagsName);
            dataMap.put("articleCategory",categoryInfo.getName());
            dataMap.put("articlePictureUrl",articlePicture.getPictureUrl());
            dataMap.put("articleTraffic",articleInfo.getTraffic());
            dataMap.put("commentNumber",commentNumber);

            if (lastArticle != null)
            {
                dataMap.put("lastStatus","200");
                dataMap.put("lastArticleId",lastArticle.getId());
                dataMap.put("lastArticleTitle",lastArticle.getTitle());
            }
            else
            {
                dataMap.put("lastStatus","404");
                dataMap.put("lastInfo","无");
            }
            if (nextArticle != null)
            {
                dataMap.put("nextStatus","200");
                dataMap.put("nextArticleId",nextArticle.getId());
                dataMap.put("nextArticleTitle",nextArticle.getTitle());
            }
            else
            {
                dataMap.put("nextStatus","404");
                dataMap.put("nextInfo","无");

            }

            return DataMap.success().setData(dataMap);
        }

        return DataMap.fail(CodeType.ARTICLE_NOT_EXIST);

    }

    /**
     * 当用户点击文章的时候增加阅读量
     * @param article_id
     */
    @Override
    public ArticleInfo addTrafficByArticleId(Long article_id) {
        //无线程安全问题!

        if (articleInfoMapper.selectByPrimaryKey(article_id) != null)
        {
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
            articleInfo.setTraffic(articleInfo.getTraffic() + 1);
            articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
            return articleInfo;
        }
        return null;
    }

    /**
     * 通过标签名称分页获取博客信息
     * @param tag
     * @param rows
     * @param pageNum
     * @return
     */
    @Override
    public List<PostsVo> findArticleByTag(String tag, int rows, int pageNum) {
        PageHelper.startPage(pageNum,rows);
        ArticleTagExample example = new ArticleTagExample();
        example.or().andTagNameEqualTo(tag);
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example);
        PageInfo<ArticleTag> pageInfo = new PageInfo<>(articleTagList);
        List<PostsVo> postsVoList = new ArrayList<>();
        //获取博客的信息
        for (ArticleTag articleTag : articleTagList)
        {
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleTag.getArticleId());
            String categoryName = categoryService.findCategoryByArticleId(articleTag.getArticleId()).getName();
            List<String> articleTagNames = tagService.findTagsNameByArticleId(articleTag.getArticleId());

            //填充前端需要数据的包装类
            PostsVo postsVo = new PostsVo();
            postsVo.setViews(articleInfo.getTraffic());
            postsVo.setTagsName(tag);
            postsVo.setCategoryName(categoryName);
            Date createTime = articleInfo.getCreateBy();
            Instant instant = createTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            postsVo.setCreateTime(instant.atZone(zoneId).toLocalDateTime());
            postsVo.setTitle(articleInfo.getTitle());
            postsVo.setSummary(articleInfo.getSummary());
            postsVo.setId(articleInfo.getId());
            //填充文章图片信息
            ArticlePictureExample example3 = new ArticlePictureExample();
            example3.or().andArticleIdEqualTo(articleInfo.getId());
            if (articlePictureMapper.selectByExample(example3).isEmpty())
            {
                postsVo.setThumbnail(null);
            }
            else
            {
                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example3).get(0);

                postsVo.setThumbnail(articlePicture.getPictureUrl());
            }

            //填充评论数
            int commentNum = commentService.findCommentNumByArticleId(articleInfo.getId());
            postsVo.setComments(commentNum);


            postsVoList.add(postsVo);
        }

        return postsVoList;
    }

    @Override
    public void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId) {
        if ("lastArticleId".equals(lastOrNext))
        {
            ArticleInfo updateArticle = articleInfoMapper.selectByPrimaryKey(articleId);
            updateArticle.setLastarticleid(lastOrNextArticleId);
            articleInfoMapper.updateByPrimaryKeySelective(updateArticle);
        }
        if ("nextArticleId".equals(lastOrNext))
        {
            ArticleInfo updateArticle = articleInfoMapper.selectByPrimaryKey(articleId);
            updateArticle.setNextarticleid(lastOrNextArticleId);
            articleInfoMapper.updateByPrimaryKeySelective(updateArticle);
        }
    }

    @Override
    public DataMap findAllArticleByPage(int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        //创建一个List<包装类>，包装博客详细信息
        List<ArticleInfoDto> articleInfoDtoList = new ArrayList<>();

        //查询所有博客基础信息
        ArticleInfoExample example = new ArticleInfoExample();
        example.or();
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByExample(example);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleInfoList);

        //将详细信息填入包装类中
        for (ArticleInfo articleInfo : articleInfoList)
        {
            if (articleInfo.getId() > 10005) {
                ArticleInfoDto articleInfoDto = new ArticleInfoDto();

                //填充基础信息
                articleInfoDto.setId(articleInfo.getId());
                articleInfoDto.setTraffic(articleInfo.getTraffic());
                articleInfoDto.setCreateBy(articleInfo.getCreateBy());
                articleInfoDto.setLikes(articleInfo.getLikes());
                articleInfoDto.setTitle(articleInfo.getTitle());

                //填充分类信息
                ArticleCategoryExample example1 = new ArticleCategoryExample();
                example1.or().andArticleIdEqualTo(articleInfo.getId());
                ArticleCategory articleCategory = articleCategoryMapper.selectByExample(example1).get(0);
                articleInfoDto.setCategoryName(categoryInfoMapper.selectByPrimaryKey(articleCategory.getCategoryId()).getName());

                //填充标签信息
                ArticleTagExample example2 = new ArticleTagExample();
                example2.or().andArticleIdEqualTo(articleInfo.getId());
                List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < articleTagList.size(); i++) {
                    stringBuilder.append(articleTagList.get(i).getTagName());
                    if (i != articleTagList.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                articleInfoDto.setStringTagsName(stringBuilder.toString());

                //填充评论数
                int commentNum = commentService.findCommentNumByArticleId(articleInfo.getId());
                articleInfoDto.setCommentNum(commentNum);

                articleInfoDtoList.add(articleInfoDto);
            }
        }

        JSONObject articleJson;
        JSONArray articleJsonArray = new JSONArray();

        //填充DataMap
        for (ArticleInfoDto articleInfoDto : articleInfoDtoList)
        {
            articleJson = new JSONObject();
            articleJson.put("id",articleInfoDto.getId());
            articleJson.put("title",articleInfoDto.getTitle());
            articleJson.put("category",articleInfoDto.getCategoryName());
            articleJson.put("tag",articleInfoDto.getStringTagsName());
            articleJson.put("likes",articleInfoDto.getLikes());
            articleJson.put("traffic",articleInfoDto.getTraffic());
            articleJson.put("commentNum",articleInfoDto.getCommentNum());
            articleJson.put("createBy",DateFormatUtil.getDateString(articleInfoDto.getCreateBy()));
            articleJsonArray.add(articleJson);
        }


        return DataMap.success().setData(articleJsonArray);
    }

    @Override
    public int findArticleCount() {
       return articleInfoMapper.findArticleCount();
    }

    @Override
    public DataMap getDraftArticle(ArticleInfoDto articleInfoDto,String content) {
        Map<String,Object> dataMap = new HashMap<>(6);
        dataMap.put("articleTitle",articleInfoDto.getTitle());
        dataMap.put("articleCategory",articleInfoDto.getCategoryName());
        dataMap.put("articleContent",content);
        dataMap.put("articleTagsName",articleInfoDto.getArticleTagNames());
        dataMap.put("articlePictureUrl",articleInfoDto.getPictureUrl());

        return DataMap.success().setData(dataMap);

    }

    @Override
    @Cacheable(value = "article",key = "#article_id")
    public com.alibaba.fastjson.JSONObject findArticleByIdWithRedis(Long article_id) {
        //获取文章的相关对象
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
        ArticleContentExample example = new ArticleContentExample();
        example.or().andArticleIdEqualTo(article_id);
        ArticleContent articleContent = articleContentMapper.selectByExample(example).get(0);
        ArticlePictureExample example1 = new ArticlePictureExample();
        example1.or().andArticleIdEqualTo(article_id);
        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);
        ArticleTagExample example2 = new ArticleTagExample();
        example2.or().andArticleIdEqualTo(article_id);
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);

        //分类信息
        CategoryInfo categoryInfo = categoryService.findCategoryByArticleId(article_id);

        //上一篇博客以及下一篇博客的信息
        ArticleInfo lastArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getLastarticleid());
        ArticleInfo nextArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getNextarticleid());

        List<String> articleTagsName = new ArrayList<>();
        for (ArticleTag articleTag : articleTagList)
        {
            articleTagsName.add(articleTag.getTagName());
        }
        //将文章添加到DataMap与前端交互(充当dto层)
        if (articleInfo!=null && articleContent!=null && articlePicture!=null && articleTagList!=null)
        {
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id",article_id);
            dataMap.put("articleTitle",articleInfo.getTitle());
            dataMap.put("publishDate",DateFormatUtil.getDateString(articleInfo.getCreateBy()));
            dataMap.put("articleContent",articleContent.getContent());
            dataMap.put("articleTags",articleTagList);
            dataMap.put("articleTagsName",articleTagsName);
            dataMap.put("articleCategory",categoryInfo.getName());
            dataMap.put("articlePictureUrl",articlePicture.getPictureUrl());

            if (lastArticle != null)
            {
                dataMap.put("lastStatus","200");
                dataMap.put("lastArticleId",lastArticle.getId());
                dataMap.put("lastArticleTitle",lastArticle.getTitle());
            }
            else
            {
                dataMap.put("lastStatus","404");
                dataMap.put("lastInfo","无");
            }
            if (nextArticle != null)
            {
                dataMap.put("nextStatus","200");
                dataMap.put("nextArticleId",nextArticle.getId());
                dataMap.put("nextArticleTitle",nextArticle.getTitle());
            }
            else
            {
                dataMap.put("nextStatus","404");
                dataMap.put("nextInfo","无");

            }

            return com.alibaba.fastjson.JSONObject.parseObject(JSON.toJSONString(dataMap));
        }

        return null;
    }

    /**
     * sakura 接口service
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PostsVo> findPostsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PostsVo> postsVoList = new ArrayList<>();
        //查询所有博客基础信息
        ArticleInfoExample example = new ArticleInfoExample();
        example.setOrderByClause("id desc");
        example.or();
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByExample(example);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleInfoList);

        //将详细信息填入包装类中
        for (ArticleInfo articleInfo : articleInfoList)
        {
            if(articleInfo.getId() > 10005) {
                PostsVo postsVo = new PostsVo();

                //填充基础信息
                postsVo.setId(articleInfo.getId());
                postsVo.setViews(articleInfo.getTraffic());
                Date createTime = articleInfo.getCreateBy();
                Instant instant = createTime.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                postsVo.setCreateTime(instant.atZone(zoneId).toLocalDateTime());
                postsVo.setTitle(articleInfo.getTitle());
                postsVo.setSummary(articleInfo.getSummary());


                //填充分类信息
                ArticleCategoryExample example1 = new ArticleCategoryExample();
                example1.or().andArticleIdEqualTo(articleInfo.getId());
                ArticleCategory articleCategory = articleCategoryMapper.selectByExample(example1).get(0);
                postsVo.setCategoryName(categoryInfoMapper.selectByPrimaryKey(articleCategory.getCategoryId()).getName());
                postsVo.setCategoryId(articleCategory.getCategoryId());
                //填充标签信息
                ArticleTagExample example2 = new ArticleTagExample();
                example2.or().andArticleIdEqualTo(articleInfo.getId());
                List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < articleTagList.size(); i++) {
                    stringBuilder.append(articleTagList.get(i).getTagName());
                    if (i != articleTagList.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                postsVo.setTagsName(stringBuilder.toString());

                //填充文章图片信息
                ArticlePictureExample example3 = new ArticlePictureExample();
                example3.or().andArticleIdEqualTo(articleInfo.getId());
                if (articlePictureMapper.selectByExample(example3).isEmpty()) {
                    postsVo.setThumbnail("http://static.lhanman.cn/public/blogCover/ServerError.jpeg");
                } else {
                    ArticlePicture articlePicture = articlePictureMapper.selectByExample(example3).get(0);

                    postsVo.setThumbnail(articlePicture.getPictureUrl());
                }

                //填充评论数
                int commentNum = commentService.findCommentNumByArticleId(articleInfo.getId());
                postsVo.setComments(commentNum);

                postsVoList.add(postsVo);
            }
        }
        return postsVoList;
    }

    @Override
    public List<PostsVo> findHottestPostsByView(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PostsVo> postsVoList = new ArrayList<>();
        //查询所有博客基础信息
        ArticleInfoExample example = new ArticleInfoExample();
        example.setOrderByClause("traffic desc");
        example.or();
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByExample(example);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleInfoList);

        //将详细信息填入包装类中
        for (ArticleInfo articleInfo : articleInfoList)
        {
            PostsVo postsVo = new PostsVo();

            //填充基础信息
            postsVo.setId(articleInfo.getId());
            postsVo.setViews(articleInfo.getTraffic());
            Date createTime = articleInfo.getCreateBy();
            Instant instant = createTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            postsVo.setCreateTime(instant.atZone(zoneId).toLocalDateTime());
            postsVo.setTitle(articleInfo.getTitle());
            postsVo.setSummary(articleInfo.getSummary());


            //填充分类信息
            ArticleCategoryExample example1 = new ArticleCategoryExample();
            example1.or().andArticleIdEqualTo(articleInfo.getId());
            ArticleCategory articleCategory = articleCategoryMapper.selectByExample(example1).get(0);
            postsVo.setCategoryName(categoryInfoMapper.selectByPrimaryKey(articleCategory.getCategoryId()).getName());

            //填充标签信息
            ArticleTagExample example2 = new ArticleTagExample();
            example2.or().andArticleIdEqualTo(articleInfo.getId());
            List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example2);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<articleTagList.size(); i++)
            {
                stringBuilder.append(articleTagList.get(i).getTagName());
                if (i != articleTagList.size()-1)
                {
                    stringBuilder.append(",");
                }
            }
            postsVo.setTagsName(stringBuilder.toString());

            //填充文章图片信息
            ArticlePictureExample example3 = new ArticlePictureExample();
            example3.or().andArticleIdEqualTo(articleInfo.getId());
            if (articlePictureMapper.selectByExample(example3).isEmpty())
            {
                postsVo.setThumbnail(null);
            }
            else
            {
                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example3).get(0);

                postsVo.setThumbnail(articlePicture.getPictureUrl());
            }

            //填充评论数
            int commentNum = commentService.findCommentNumByArticleId(articleInfo.getId());
            postsVo.setComments(commentNum);

            postsVoList.add(postsVo);
        }
        return postsVoList;
    }

    @Override
    public PostsVo findPostsById(Long article_id) {
        //获取文章的相关对象
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(article_id);
        ArticleContentExample example = new ArticleContentExample();
        example.or().andArticleIdEqualTo(article_id);
        List<ArticleContent> articleContentList = articleContentMapper.selectByExample(example);
        ArticleContent articleContent;
        if (articleContentList.size() == 0)
        {
            return null;
        }
        else
        {
            articleContent = articleContentList.get(0);
        }
        ArticlePictureExample example1 = new ArticlePictureExample();
        example1.or().andArticleIdEqualTo(article_id);
        ArticlePicture articlePicture = articlePictureMapper.selectByExample(example1).get(0);

        //评论信息
        int commentNumber=commentService.findCommentNumByArticleId(article_id);

        //上一篇博客以及下一篇博客的信息
        ArticleInfo lastArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getLastarticleid());
        ArticleInfo nextArticle = articleInfoMapper.selectByPrimaryKey(articleInfo.getNextarticleid());


        //将文章添加到PostsVo与前端交互(充当dto层)
        if (articleInfo!=null && articleContent!=null)
        {
            Date createTime = articleInfo.getCreateBy();
            Instant instant = createTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            PostsVo postsVo = new PostsVo();
            postsVo.setId(article_id);
            postsVo.setCreateTime(instant.atZone(zoneId).toLocalDateTime())
                    .setSummary(articleInfo.getSummary())
                    .setTitle(articleInfo.getTitle())
                    .setThumbnail(articlePicture.getPictureUrl())
                    .setIsComment(1)
                    .setViews(articleInfo.getTraffic())
                    .setComments(commentNumber)
                    .setContent(articleContent.getContent());


            if (lastArticle != null)
            {
                ArticlePictureExample example2 = new ArticlePictureExample();
                example2.or().andArticleIdEqualTo(lastArticle.getId());
                postsVo.setLastStatus("200")
                        .setLastArticleId(lastArticle.getId())
                        .setLastTitle(lastArticle.getTitle())
                        .setLastPicUrl(articlePictureMapper.selectByExample(example2).get(0).getPictureUrl());
            }
            else
            {
                  postsVo.setLastStatus("404");
            }
            if (nextArticle != null)
            {
                ArticlePictureExample example3 = new ArticlePictureExample();
                example3.or().andArticleIdEqualTo(nextArticle.getId());
                postsVo.setNextStatus("200")
                        .setNextArticleId(nextArticle.getId())
                        .setNextTitle(nextArticle.getTitle())
                        .setNextPicUrl(articlePictureMapper.selectByExample(example3).get(0).getPictureUrl());
            }
            else
            {
                postsVo.setNextStatus("404");
            }

            return postsVo;
        }

        return null;
    }

    @Override
    public List<PostsVo> findAllPostsOrderByTime() {
        List<PostsVo> postsVoList = articleInfoMapper.selectArchiveTotalGroupTimeline();

        postsVoList.forEach(postsVo -> {
            //查询每一个时间点的文章
            postsVo.setArchivePosts(articleInfoMapper.selectByArchiveDate(postsVo.getArchiveDate()));

        });
        return postsVoList;
    }

    @Override
    public List<PostsVo> findPostsByCategoryId(Long categoryId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        ArticleCategoryExample example = new ArticleCategoryExample();
        example.or().andCategoryIdEqualTo(categoryId);
        List<ArticleCategory> articleCategoryList = articleCategoryMapper.selectByExample(example);

        PageInfo<ArticleCategory> pageInfo = new PageInfo<>(articleCategoryList);
        List<PostsVo> postsVoList = new ArrayList<>();
        //获取博客的信息
        for (ArticleCategory articleCategory : articleCategoryList)
        {
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleCategory.getArticleId());
            String categoryName = categoryService.findCategoryByArticleId(articleCategory.getArticleId()).getName();
            List<String> articleTagNames = tagService.findTagsNameByArticleId(articleCategory.getArticleId());

            //填充前端需要数据的包装类
            PostsVo postsVo = new PostsVo();
            postsVo.setViews(articleInfo.getTraffic());
            postsVo.setCategoryName(categoryName);
            Date createTime = articleInfo.getCreateBy();
            Instant instant = createTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            postsVo.setCreateTime(instant.atZone(zoneId).toLocalDateTime());
            postsVo.setTitle(articleInfo.getTitle());
            postsVo.setSummary(articleInfo.getSummary());
            postsVo.setId(articleInfo.getId());
            //填充文章图片信息
            ArticlePictureExample example3 = new ArticlePictureExample();
            example3.or().andArticleIdEqualTo(articleInfo.getId());
            if (articlePictureMapper.selectByExample(example3).isEmpty())
            {
                postsVo.setThumbnail(null);
            }
            else
            {
                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example3).get(0);

                postsVo.setThumbnail(articlePicture.getPictureUrl());
            }

            //填充评论数
            int commentNum = commentService.findCommentNumByArticleId(articleInfo.getId());
            postsVo.setComments(commentNum);


            postsVoList.add(postsVo);
        }

        return postsVoList;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"article"},allEntries = true)
    public void refreshBlogCache() {

    }


    //==================================================Override分割线=======================================//




    // 为ArticleInfoDto填充数据
    public void fillArticleInfoDto(List<ArticleInfoDto> articles,List<ArticleCategory> articleCategories,Long category_id)
    {
        CategoryInfo categoryInfo = categoryInfoMapper.selectByPrimaryKey(category_id);
        String categoryName = categoryInfo.getName();
        for (int i=0; i<articleCategories.size(); i++)
        {
            Long articleId = articleCategories.get(i).getArticleId();
            ArticleInfoDto articleInfoDto = new ArticleInfoDto();

            //填充博客基础信息
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleId);
            articleInfoDto.setId(articleInfo.getId());
            articleInfoDto.setTitle(articleInfo.getTitle());
            articleInfoDto.setSummary(articleInfo.getSummary());
            articleInfoDto.setCreateBy(articleInfo.getCreateBy());
            articleInfoDto.setTraffic(articleInfo.getTraffic());
            articleInfoDto.setCategoryName(categoryName);
            //填充博客图片信息
            ArticlePictureExample example2 = new ArticlePictureExample();
            example2.or().andArticleIdEqualTo(articleInfo.getId());
            if (articlePictureMapper.selectByExample(example2).isEmpty())
            {
                articleInfoDto.setPictureUrl("assets/images/ServerError.jpg");
            }
            else
            {
                ArticlePicture articlePicture = articlePictureMapper.selectByExample(example2).get(0);
                articleInfoDto.setArticlePictureId(articlePicture.getId());
                articleInfoDto.setPictureUrl(articlePicture.getPictureUrl());
            }


            //填充博客标签信息
            ArticleTagExample example3 = new ArticleTagExample();
            example3.or().andArticleIdEqualTo(articleId);
            List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example3);
            List<String> articleTagNames = new ArrayList<>();
            for (ArticleTag articleTag : articleTagList)
            {
                articleTagNames.add(articleTag.getTagName());
            }
            articleInfoDto.setArticleTagNames(articleTagNames);
            //填充文章评论信息
            int commentNum = commentService.findCommentNumByArticleId(articleId);
            articleInfoDto.setCommentNum(commentNum);

            articles.add(articleInfoDto);
        }

    }
}
