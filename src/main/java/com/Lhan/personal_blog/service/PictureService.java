package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.pojo.ArticlePicture;
import com.Lhan.personal_blog.util.DataMap;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    int findPictureNum();

    DataMap findAllPictureByPage(int pageNum, int pageSize);

    DataMap deletePictureByPictureId(ArticlePicture articlePicture);

    void updatePictureByPictureId(ArticlePicture articlePicture, MultipartFile file);

}
