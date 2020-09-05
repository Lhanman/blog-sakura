package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.constant.OSSClientConstants;
import com.Lhan.personal_blog.dto.PictureDto;
import com.Lhan.personal_blog.mapper.ArticleInfoMapper;
import com.Lhan.personal_blog.mapper.ArticlePictureMapper;
import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.pojo.ArticlePicture;
import com.Lhan.personal_blog.pojo.ArticlePictureExample;
import com.Lhan.personal_blog.service.PictureService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.util.FileUtil;
import com.Lhan.personal_blog.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Resource
    ArticlePictureMapper articlePictureMapper;

    @Resource
    ArticleInfoMapper articleInfoMapper;

    @Override
    public int findPictureNum() {
        return articlePictureMapper.findPictureNum();
    }

    @Override
    public DataMap findAllPictureByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<PictureDto> pictureDtoList = new ArrayList<>();

        ArticlePictureExample example = new ArticlePictureExample();
        example.or();
        List<ArticlePicture> articlePictureList = articlePictureMapper.selectByExample(example);
        PageInfo<ArticlePicture> pageInfo = new PageInfo<>(articlePictureList);

        for (ArticlePicture articlePicture : articlePictureList)
        {
            PictureDto pictureDto = new PictureDto();
            pictureDto.setId(articlePicture.getId());
            pictureDto.setPicture_url(articlePicture.getPictureUrl());
            pictureDto.setCreateBy(DateFormatUtil.getDateString(articlePicture.getCreateBy()));
            pictureDto.setModifiedBy(DateFormatUtil.getDateString(articlePicture.getModifiedBy()));
            pictureDto.setArticle_id(articlePicture.getArticleId());

            //获取博客标题
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articlePicture.getArticleId());
            //填充标题
            pictureDto.setTitle(articleInfo.getTitle());

            pictureDtoList.add(pictureDto);
        }

        JSONArray pictureJsonArray = JSONArray.fromObject(pictureDtoList);
        return DataMap.success().setData(pictureJsonArray);
    }

    @Override
    @CacheEvict(value = "article",key = "#articlePicture.articleId")
    @Transactional
    public DataMap deletePictureByPictureId(ArticlePicture articlePicture) {
        String pictureUrl = articlePicture.getPictureUrl();
        pictureUrl = pictureUrl.substring(pictureUrl.indexOf("blogCover"));
        FileUtil fileUtil = new FileUtil();

        if (pictureUrl.contains("ServerError"))
        {
            return DataMap.fail();
        }

        fileUtil.deleteFile(pictureUrl);

        articlePicture.setPictureUrl(OSSClientConstants.PICTURE_NOT_FOUND_URL);

        articlePictureMapper.updateByPrimaryKeySelective(articlePicture);
        return DataMap.success();
    }

    @Override
    @CacheEvict(value = "article",key = "#articlePicture.articleId")
    @Transactional
    public void updatePictureByPictureId(ArticlePicture articlePicture, MultipartFile file) {


        FileUtil fileUtil = new FileUtil();
        String pictureUrl = articlePicture.getPictureUrl();
        pictureUrl = pictureUrl.substring(pictureUrl.indexOf("blogCover"));

        if (!pictureUrl.contains("ServerError"))
        {
            fileUtil.deleteFile(pictureUrl);
        }
        DateFormatUtil dateFormatUtil = new DateFormatUtil();
//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogCover/";
        String fileContentType = file.getContentType();
        String fileExtension = fileContentType.substring(fileContentType.indexOf("/") +1);
        String fileName = dateFormatUtil.getLongTime()+"."+fileExtension;
        String subCatalog = "blogCover/"+DateFormatUtil.getDateString(new Date());
        String newPictureUrl = fileUtil.uploadFile(file,subCatalog,fileName);
        articlePicture.setPictureUrl(newPictureUrl);
        articlePicture.setModifiedBy(DateFormatUtil.dateFormat(new Date()));
        articlePictureMapper.updateByPrimaryKeySelective(articlePicture);
    }

}
