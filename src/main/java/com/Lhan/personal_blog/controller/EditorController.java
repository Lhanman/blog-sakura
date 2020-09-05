package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.aspect.annotation.AdminCheck;
import com.Lhan.personal_blog.aspect.annotation.PermissionCheck;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.ArticleInfoDto;
import com.Lhan.personal_blog.mapper.ArticlePictureMapper;
import com.Lhan.personal_blog.mapper.CategoryInfoMapper;
import com.Lhan.personal_blog.pojo.*;
import com.Lhan.personal_blog.util.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 *  发表博客
 *
 */
@RestController
public class EditorController extends BaseController {

    //全局变量 博客封面路径
    private static String pictureUrl;
    /**
     * 发表博客
     *
     */
    @PostMapping(value = "article/publishArticle",produces = MediaType.APPLICATION_JSON_VALUE)
    public String publishArticle(@RequestParam("articleTitle") String articleTitle,
                                 @RequestParam("articleContent") String content,
                                 @RequestParam("articleTagsValue") String[] articleTagsValue,
                                 @RequestParam("articleCategory") String categoryName,
                                 @RequestParam("articleHtmlContent") String articleHtmlContent,
                                 @AuthenticationPrincipal Principal principal,
                                 HttpServletRequest request)
    {
        //判断是否登录超时
        if (principal == null)
        {
            ArticleInfoDto articleInfoDto = new ArticleInfoDto();
            articleInfoDto.setTitle(articleTitle);
            articleInfoDto.setPictureUrl(pictureUrl);
            articleInfoDto.setCategoryName(categoryName);
            articleInfoDto.setArticleTagNames(Arrays.asList(articleTagsValue));
            request.getSession().setAttribute("article",articleInfoDto);
            request.getSession().setAttribute("content",content);
            return JsonResult.fail(CodeType.USER_NOT_LOGIN).toJSON();
        }
        String username = principal.getName();

        if (!userService.isSuperAdmin(username))
        {
            return JsonResult.fail(CodeType.PUBLISH_ARTICLE_NO_PERMISSION).toJSON();
        }
        //判断是否有添加博客封面
        if (pictureUrl == null)
        {
            return JsonResult.fail(CodeType.BLOG_PICTURE_NULL).toJSON();
        }

        //创建Article相关对象
        ArticleContent articleContent = new ArticleContent();
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleCategory articleCategory = new ArticleCategory();
        ArticlePicture articlePicture = new ArticlePicture();
        List<ArticleTag> articleTagList = new ArrayList<>();

        //获得文章摘要
        BuildArticleSummaryUtil buildArticleSummaryUtil = new BuildArticleSummaryUtil();
        String articleSummary = buildArticleSummaryUtil.buildArticleSummary(articleHtmlContent);

        String id = request.getParameter("id");


        /**
         * 若是修改文章
         */
        if (!StringUtil.BLANK.equals(id) && id != null)
        {
            //设置ArticleInfo基础信息并与数据库交互
            articleInfo.setSummary(articleSummary+"...");
            articleInfo.setTitle(articleTitle);
            articleInfo.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
            articleInfo.setId(Long.parseLong(id));
            articleService.updateArticleInfo(articleInfo);

            ////设置ArticleContent基础信息，并与数据库交互
            articleContent.setContent(content);
            articleContent.setModifieldBy(DateFormatUtil.dateFormat(new Date()));
            //通过articleId找ArticleContent
            ArticleContentExample example = new ArticleContentExample();
            example.or().andArticleIdEqualTo(Long.parseLong(id));
            articleContent.setId(articleContentMapper.selectByExample(example).get(0).getId());
            articleService.updateArticleContent(articleContent);

            //设置CategoryInfo基础信息，并与数据库交互
            ArticleCategoryExample example2 = new ArticleCategoryExample();
            example2.or().andArticleIdEqualTo(Long.parseLong(id));
            CategoryInfo oldCategoryInfo = categoryInfoMapper.selectByPrimaryKey(articleCategoryMapper.selectByExample(example2).get(0).getCategoryId());
            if (!oldCategoryInfo.getName().equals(categoryName))
            {
                CategoryInfo newCategoryInfo = categoryService.findCategoryByName(categoryName);
                newCategoryInfo.setNumber(newCategoryInfo.getNumber() +1);
                oldCategoryInfo.setNumber(oldCategoryInfo.getNumber() -1);
                categoryService.updateCategory(newCategoryInfo);
                categoryService.updateCategory(oldCategoryInfo);
            }


            //设置ArticleCategory基础信息,并与数据库交互
            if (!oldCategoryInfo.getName().equals(categoryName))
            {
                ArticleCategoryExample example1 = new ArticleCategoryExample();
                example1.or().andArticleIdEqualTo(Long.parseLong(id));
                ArticleCategory articleCategory1 = articleCategoryMapper.selectByExample(example1).get(0);
                articleCategory1.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
                articleCategory1.setCategoryId(categoryService.findCategoryByName(categoryName).getId());
                articleService.updateArticleCategory(articleCategory1);
            }

            //设置ArticleTag基础信息，并与数据库交互
            ArticleTagExample articleTagExample = new ArticleTagExample();
            articleTagExample.or().andArticleIdEqualTo(Long.parseLong(id));

            List<ArticleTag> oldArticleTagList = articleTagMapper.selectByExample(articleTagExample);

            String[] tags = new String[articleTagsValue.length];
            for (int i = 0; i< articleTagsValue.length; i++)
            {
                //去掉可能出现的换行符
                articleTagsValue[i] = articleTagsValue[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("&nbsp;",StringUtil.BLANK)
                        .replaceAll("\\s",StringUtil.BLANK).trim();
                tags[i] = articleTagsValue[i];
            }
            //新的标签
            List<String> oldTagNameList = new ArrayList<>();

            for (ArticleTag articleTag : oldArticleTagList)
            {
                oldTagNameList.add(articleTag.getTagName());
            }

            for(String tagName : tags)
            {
                if (!oldTagNameList.contains(tagName))
                {
                    ArticleTag tag = new ArticleTag();
                    tag.setArticleId(Long.parseLong(id));
                    tag.setCreateBy(DateFormatUtil.dateFormat(new Date()));
                    tag.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
                    tag.setIsEffective(true);
                    tag.setTagName(tagName);
                    articleTagList.add(tag);
                }
            }
            articleTagService.addArticleTagBatch(articleTagList);
            return JsonResult.success(CodeType.BLOG_UPDATE_SUCCESS).toJSON();
        }

        /**
         *
         * 若不是修改文章
         */

        //设置ArticleInfo基础信息并与数据库交互
        articleInfo.setSummary(articleSummary+"...");
        articleInfo.setTitle(articleTitle);
        articleInfo.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articleInfo.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        articleInfo.setIsTop(false);
        articleInfo.setTraffic(0);
        articleInfo.setLikes(0);
        articleService.addArticleInfo(articleInfo);

        //设置ArticleContent基础信息，并与数据库交互
        articleContent.setContent(content);
        articleContent.setArticleId(articleService.getArticleLatestId());
        articleContent.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articleContent.setModifieldBy(DateFormatUtil.dateFormat(new Date()));
        articleService.addArticleContent(articleContent);

        //设置CategoryInfo基础信息，并与数据库交互
        CategoryInfo categoryInfo = categoryService.findCategoryByName(categoryName);
        Integer oldNumber = categoryInfo.getNumber();
        Integer newNumber = oldNumber + 1;
        categoryInfo.setNumber(newNumber);
        categoryService.updateCategory(categoryInfo);

        //设置ArticleCategory基础信息,并与数据库交互
        articleCategory.setArticleId(articleService.getArticleLatestId());
        articleCategory.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articleCategory.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        articleCategory.setCategoryId(categoryInfo.getId());
        categoryService.addArticleCategory(articleCategory);

        //设置ArticlePicture基础信息,并与数据库交互
        articlePicture.setArticleId(articleService.getArticleLatestId());
        articlePicture.setCreateBy(DateFormatUtil.dateFormat(new Date()));
        articlePicture.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        articlePicture.setPictureUrl(pictureUrl);
        articleService.addArticlePicture(articlePicture);

        //设置ArticleTag基础信息，并与数据库交互

        String[] articleTags = articleTagsValue;
        String[] tags = new String[articleTags.length];
        for (int i=0; i<articleTags.length; i++)
        {
            //去掉可能出现的换行符
            articleTags[i] = articleTags[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("&nbsp;",StringUtil.BLANK)
                                            .replaceAll("\\s",StringUtil.BLANK).trim();
            tags[i] = articleTags[i];
        }
        for (int j=0; j<tags.length; j++)
        {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleService.getArticleLatestId());
            articleTag.setCreateBy(DateFormatUtil.dateFormat(new Date()));
            articleTag.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
            articleTag.setIsEffective(true);
            articleTag.setTagName(tags[j]);
            articleTagList.add(articleTag);
        }
        articleTagService.addArticleTagBatch(articleTagList);

        DataMap data = articleService.findArticleInfoById(articleService.getArticleLatestId());
        return JsonResult.build(data).toJSON();
    }

    @GetMapping(value = "category/findCategoriesName",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findCategoriesName()
    {
        DataMap data = categoryService.findCategoriesName();
        return JsonResult.build(data).toJSON();
    }

    @RequestMapping("/uploadImage")
    public Map<String,Object> uploadImage(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "editormd-image-file",required = false)MultipartFile file
                                         )
    {
        Map<String,Object> resultMap = new HashMap<>();

        try
        {
            request.setCharacterEncoding("utf-8");
            //设置返回头后页面才能返回url
            response.setHeader("X-Frame-Options","SAMEORIGIN");

            DateFormatUtil dateFormatUtil = new DateFormatUtil();
//            String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
            String fileContentType = file.getContentType();
            String fileExtension = fileContentType.substring(fileContentType.indexOf("/") +1);
            String fileName = dateFormatUtil.getLongTime() + "." +fileExtension;

            String subCatalog = "blog/" + DateFormatUtil.getDateString(new Date());
            FileUtil fileUtil = new FileUtil();
            String fileUrl = fileUtil.uploadFile(file,subCatalog,fileName);

            resultMap.put("success",1);
//            resultMap.put("message","上传成功");
            resultMap.put("url",fileUrl);

        }
        catch (Exception e)
        {
            resultMap.put("success",0);
            resultMap.put("message","上传失败");
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping("/uploadBlogCover")
    @AdminCheck(value = "ROLE_USER")
    @ResponseBody
    public String uploadBlogCover(@RequestParam(value = "file",required = false) MultipartFile file,@AuthenticationPrincipal Principal principal)
    {
        DateFormatUtil dateFormatUtil = new DateFormatUtil();
        String username = principal.getName();
        if (!userService.isSuperAdmin(username))
        {
            return JsonResult.fail(CodeType.PUBLISH_ARTICLE_NO_PERMISSION).toJSON();
        }
        if (file == null)
        {
            return JsonResult.fail(CodeType.BLOG_PICTURE_NULL).toJSON();
        }
//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogCover/";
        String fileContentType = file.getContentType();
        String fileExtension = fileContentType.substring(fileContentType.indexOf("/") +1);
        String fileName = dateFormatUtil.getLongTime()+"."+fileExtension;
        String subCatalog = "blogCover/"+DateFormatUtil.getDateString(new Date());
        FileUtil fileUtil = new FileUtil();
        pictureUrl = fileUtil.uploadFile(file,subCatalog,fileName);
        return JsonResult.success().toJSON();
    }


    @GetMapping(value = "/getDraftArticle",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getDraftArticle(HttpServletRequest request)
    {
        String id = (String) request.getSession().getAttribute("id");

        //判断是否为修改文章
        if (id!=null)
        {
            request.getSession().removeAttribute("id");
            ArticlePictureExample example = new ArticlePictureExample();
            example.or().andArticleIdEqualTo(Long.parseLong(id));
            ArticlePicture articlePicture = articlePictureMapper.selectByExample(example).get(0);
            pictureUrl = articlePicture.getPictureUrl();
            DataMap data = articleService.findArticleById(Long.parseLong(id));
            return JsonResult.build(data).toJSON();
        }

        //判断是否写文章登录超时
        if (request.getSession().getAttribute("article") != null)
        {
            ArticleInfoDto articleInfoDto = (ArticleInfoDto) request.getSession().getAttribute("article");
            String content = (String) request.getSession().getAttribute("content");
            DataMap data = articleService.getDraftArticle(articleInfoDto,content);
            request.getSession().removeAttribute("article");
            request.getSession().removeAttribute("content");
            pictureUrl = articleInfoDto.getPictureUrl();
            return JsonResult.build(data).toJSON();
        }

        return JsonResult.fail().toJSON();
    }


    @GetMapping(value = "/verifyId",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @AdminCheck(value = "ROLE_USER")
    public String verifyId(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        if (userService.isSuperAdmin(username))
        {
            return JsonResult.success().toJSON();
        }
        return JsonResult.fail().toJSON();
    }

}
