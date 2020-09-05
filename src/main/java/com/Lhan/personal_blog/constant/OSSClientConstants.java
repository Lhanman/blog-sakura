package com.Lhan.personal_blog.constant;

import com.Lhan.personal_blog.util.DataMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSSClientConstants {

    /**
     * 阿里云API的外网域名
     */
    public static final String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";

    /**
     * 阿里云API的传输加速域名
     *
     */
    public static final String SPEED_ENDPOINT = "oss-accelerate.aliyuncs.com";

    /**
     * 阿里云API的密钥Access Key ID
     */
    public static String ACCESS_KEY_ID;
    /**
     *阿里云API的密钥Access Key Secret
     */
    public static String ACCESS_KEY_SECRET;

    /**
     * 阿里云API的bucket名称
     * 在阿里云上自己创建一个bucket
     */
    public static final String BACKET_NAME = "lhanman";

    /**
     * 设置404图片url
     */
    public static String PICTURE_NOT_FOUND_URL;

    /**
     * 设置默认头像
     */
    public static String DEFAULT_MALE_AVATAR;

    public static String DEFAULT_FEMALE_AVATAR;

    /**
     * 阿里云API的文件夹名称
     * 在阿里云上自己创建一个文件夹，方便分类管理图片
     */
    public static final String FOLDER="public/";

    @Value("${aliyun.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        ACCESS_KEY_ID = accessKeyId;
    }

    @Value("${aliyun.secret}")
    public void setAccessKeySecret(String accessKeySecret) {
        ACCESS_KEY_SECRET = accessKeySecret;
    }

    @Value("${aliyun.404Url}")
    public void setPictureNotFoundUrl(String pictureNotFoundUrl)
    {
        PICTURE_NOT_FOUND_URL = pictureNotFoundUrl;
    }

    @Value("${aliyun.defaultMaleAvatar}")
    public void setDefaultMaleAvatar(String defaultMaleAvatar)
    {
        DEFAULT_MALE_AVATAR = defaultMaleAvatar;
    }

    @Value("${aliyun.defaultFemaleAvatar}")
    public void setDefaultFemaleAvatar(String defaultFemaleAvatar)
    {
        DEFAULT_FEMALE_AVATAR = defaultFemaleAvatar;
    }

}
