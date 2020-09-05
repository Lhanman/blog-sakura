package com.Lhan.personal_blog.test;

import com.Lhan.personal_blog.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTest {
//    public static void main(String[] args) throws Exception{
//        DownloadTest test = new DownloadTest();
//        String fileUrl = test.downloadCoverToServer("https://media.kitsu.io/manga/poster_images/19992/medium.jpg?1496699366","test");
//        System.out.println(fileUrl);
//    }
//
//    public String downloadCoverToServer(String urlString,String filename)throws Exception
//    {
//        //构造url
//        URL url = new URL(urlString);
//        //打开连接
//        URLConnection connection = url.openConnection();
//        connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        //设置请求超时时间
//        connection.setConnectTimeout(5*1000);
//        //输入流
//        InputStream is = connection.getInputStream();
//
//        //1kb缓冲区
//        byte[] buff = new byte[1024];
//        //读取到的数据长度
//        int len;
//
//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "Manga/";
//        File dir = new File(filePath);
//        if (!dir.exists() && !dir.isDirectory())
//        {
//            dir.mkdirs();
//        }
//        File saveFile = new File(filePath + filename+".jpg");
//        OutputStream os = new FileOutputStream(saveFile);
//        //开始读取
//        while((len = is.read(buff)) != -1)
//        {
//            os.write(buff,0,len);
//        }
//        //关闭连接
//        os.close();
//        is.close();
//        FileUtil fileUtil = new FileUtil();
//        String subCatalog = "Manga";
//        return fileUtil.uploadFile(saveFile,subCatalog);
//
//    }
}
