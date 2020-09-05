package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.constant.OSSClientConstants;
import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 上传文件到阿里云OSS
     *
     */
    public String uploadFile(MultipartFile file,String subCatalog,String fileName)
    {

        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        String key = AliYunOSSClientUtil.uploadObject2OSS(ossClient,file, OSSClientConstants.BACKET_NAME,
                                                                OSSClientConstants.FOLDER+subCatalog+"/",fileName);
        String url = AliYunOSSClientUtil.getUrl(ossClient,key);


//        //删除临时生成的文件
//        File deleteFile = new File(file.toURI());
//        deleteFile.delete();
        return url;
    }

    /**
     * 通过 InputStreams上传文件到OSS
     */
    public String uploadFile(InputStream in,String subCatalog,String fileName)
    {
        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        String key = AliYunOSSClientUtil.uploadObject2OSS(ossClient,in, OSSClientConstants.BACKET_NAME,
                OSSClientConstants.FOLDER+subCatalog+"/",fileName);
        String url = AliYunOSSClientUtil.getUrl(ossClient,key);
        return url;
    }
    /**
     * 删除阿里云OSS中的文件
     */
    public void deleteFile(String fileUrl)
    {
        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();
        AliYunOSSClientUtil.deleteFile(ossClient,OSSClientConstants.BACKET_NAME,OSSClientConstants.FOLDER + fileUrl);
    }


    /**
     * MultipartFile类型转换成File类型文件
     *
     */
    public File multipartFileToFile(MultipartFile multipartFile,String filePath,String fileName)
    {
        File file = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory())
        {
            dir.mkdirs();
        }
        if (StringUtil.BLANK.equals(multipartFile) || multipartFile.getSize() <= 0)
        {
            multipartFile = null;
        }
        else
        {
            try {
                InputStream in = multipartFile.getInputStream();
                file = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(file);
                int bytesRead = 0;
                byte[] buff = new byte[8192];
                while ((bytesRead = in.read(buff,0,8192)) != -1)
                {
                    os.write(buff,0,bytesRead);
                }
                os.close();
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 获取oss文件BiliBili目录下的文件个数
     */
    public int getNum()
    {
        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();
        return AliYunOSSClientUtil.getSum(ossClient);
    }


    /**
     * 从url中下载图片转换成File
     */
    public File url2File(String urlString) throws Exception
    {
        //构造url
        URL url = new URL(urlString);

        //打开连接
        URLConnection connection = url.openConnection();
        //设置请求超时时间
        connection.setConnectTimeout(5*1000);
        //输入流
        InputStream is = connection.getInputStream();

        //1kb缓冲区
        byte[] buff = new byte[1024];

        //读取到的数据长度
        int len;

        DateFormatUtil dateFormatUtil = new DateFormatUtil();
        String filePath = this.getClass().getResource("/").getPath().substring(1) + "Temp/";
        String fileName = dateFormatUtil.getLongTime().toString();
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory())
        {
            dir.mkdirs();
        }
        File saveFile = new File(filePath + fileName+".jpg");
        OutputStream os = new FileOutputStream(saveFile);
        //开始读取
        while((len = is.read(buff)) != -1)
        {
            os.write(buff,0,len);
        }
        //关闭连接
        os.close();
        is.close();
        return saveFile;
    }
}
