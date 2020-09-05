package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.constant.OSSClientConstants;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class AliYunOSSClientUtil {

    private static String ENDPOINT;

    private static String ACCESS_KEY_ID;

    private static String ACCESS_KEY_SECRET;

    private static String BACKET_NAME;

    private static String FOLDER;

    private static String SPEED_ENDPOINT;

    //初始化属性
    static{
        ENDPOINT = OSSClientConstants.ENDPOINT;
        SPEED_ENDPOINT = OSSClientConstants.SPEED_ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BACKET_NAME = OSSClientConstants.BACKET_NAME;
        FOLDER = OSSClientConstants.FOLDER;
    }

    /**
     * 获得阿里云OSS客户端对象
     * @return ossClient
     */
    public static OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }


    /**
     * 获得传输加速OSS客户端对象
     *
     */
    public static OSSClient getSpeedOSSClient(){
        return new OSSClient(SPEED_ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     * @param ossClient OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);

            return bucket.getName();
        }
        return bucketNames;
    }
    /**
      * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);

    }

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));

            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }


    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String filePath){
        ossClient.deleteObject(bucketName, filePath);

    }

    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
    public static  String uploadObject2OSS(OSSClient ossClient, MultipartFile file
            , String bucketName, String folder,String fileName) {
        String resultStr = null;
        try {
            //以输入流的形式上传文件

            InputStream is = file.getInputStream();

            //文件大小
            Long fileSize = file.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
            //解析结果
            resultStr = folder + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 通过InputStream上传
     */
    public static  String uploadObject2OSS(OSSClient ossClient, InputStream is
                                                , String bucketName, String folder,String fileName) {
        String resultStr = null;
        try {

            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is);
            //解析结果
            resultStr = folder + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    /**
     * 获得url链接
     * 更改为获取cdn加速域名
     *
     * @param key
     * @return
     */
    public static String getUrl(OSSClient ossClient, String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
//        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
//        URL url = ossClient.generatePresignedUrl(OSSClientConstants.BACKET_NAME, key, expiration);
//        if (url != null) {
//            return url.toString();
//        }
        String url = "http://static.lhanman.cn/"+key;
        return url;
    }

    /**
     * 获取oss文件夹文件个数
     */
    public static int getSum(OSSClient ossClient)
    {
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BACKET_NAME);
        // 设置prefix参数来获取BiliBili目录下的所有文件。
        listObjectsRequest.setPrefix(OSSClientConstants.FOLDER + "BiliBili/");

        // 递归列出BiliBili目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);

        List<OSSObjectSummary> ossObjectSummaries = listing.getObjectSummaries();

        return ossObjectSummaries.size();
    }

}
