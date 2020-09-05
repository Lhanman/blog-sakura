package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.entity.Anime;
import com.Lhan.personal_blog.entity.BiliBiliResult;
import com.Lhan.personal_blog.service.AnimeService;
import com.Lhan.personal_blog.util.*;
import com.Lhan.personal_blog.vo.AnimeVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class AnimeServiceImpl implements AnimeService {

    @Value("${BILIBILI_ANIME_METTING_URL}")
    private String BILIBILI_ANIME_MEETING_URL;
    @Value("${BILIBILI_UID}")
    private String uid;

    @Value("${BILIBILI_COOKIE}")
    private String cookie;

    @Override
    @Cacheable(value = "anime",key = "#pageNum")
    public Result getAnimeListByPage(String pageNum, String pageSize)throws Exception{
        HashMap<String,String> param = new HashMap<>();
        param.put("type","1");
        param.put("follow_status","0");
        param.put("pn",pageNum);
        param.put("ps",pageSize);
        param.put("vmid",uid);
        //时间戳
        param.put("ts",String.valueOf(System.currentTimeMillis ()));
        String result = HttpClientUtil.doGet(BILIBILI_ANIME_MEETING_URL,param,cookie);
        BiliBiliResult biliBiliResult = JsonUtil.jsonToPoJO(result,BiliBiliResult.class);

        handlerProgress(biliBiliResult);
        handlerProgressFormat(biliBiliResult);

        List<AnimeVo> animeVoList = new ArrayList<>();
        List<Anime> animeList = biliBiliResult.getData().getList();

        for (Anime anime : animeList) {
            String fileUrl =downloadCoverToServer(anime.getCover(), String.valueOf(anime.getSeason_id())+".jpg");
            anime.setCover(fileUrl);
        }


        //将番剧信息添加进DataMap中
        for (Anime anime : animeList)
        {
            AnimeVo animeVo = new AnimeVo();
            animeVo.setSession_id(anime.getSeason_id());
            animeVo.setTitle(anime.getTitle());
            animeVo.setBackground(anime.getCover());
            animeVo.setCover(anime.getCover());
            animeVo.setEvaluate(anime.getEvaluate());
            animeVo.setMyProgress(anime.getMyProgress());
            animeVo.setProgressWidth(anime.getProgressWidth());
            animeVo.setLong_title(anime.getNew_ep().getLong_title());
            animeVo.setRelease_date_show(anime.getPublish().getRelease_date_show());
            animeVo.setUrl(anime.getUrl());
            animeVoList.add(animeVo);
        }

        PageInfo pageInfo = PageUtil.initPageInfo(biliBiliResult.getData().getPn(),biliBiliResult.getData().getPs()
                ,biliBiliResult.getData().getTotal());



        return Result.createWithPaging(animeVoList,pageInfo);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"anime"},allEntries = true)
    public void refreshAnimeCache() {

    }

    /**
     * 修改追番进度,当进度为空
     *
     */
    private void handlerProgress(BiliBiliResult biliBiliResult)
    {
        List<Anime> animeList = biliBiliResult.getData().getList();
        for (Anime anime : animeList)
        {
            if (anime.getProgress().equals(""))
            {
                anime.setProgress("尚未观看");
            }

        }
    }

    /**
     * 修改追番进度返回String格式
     *
     */
    private void handlerProgressFormat(BiliBiliResult biliBiliResult)
    {
        List<Anime> animeList = biliBiliResult.getData().getList();
        for (Anime anime : animeList)
        {
            String epsNum = "未知";
            if(anime.getIs_finish().equals("1"))
            {
                epsNum = anime.getTotal_count();
            }
            String progress = anime.getProgress();
            //分割字符串，提取progressNum
            String[] progressNumArray = progress.split("\\s+");

            String progressNum = progressNumArray[0];
            String myProgress = "";
            //获取处理后的progress
            if (anime.getIs_finish().equals("1"))
            {
                myProgress = progressNum + ",共 "+ epsNum + " 话";
            }
            else
            {
                myProgress = progressNum + ", 未完结";
            }
            progressNum = getNum(progressNum).equals("") ?"0":getNum(progressNum);

            //获取进度条
            double  progressWidth = 0;
            if (epsNum.equals("未知"))
            {
                progressWidth = 50;
            }

            else
            {
                DecimalFormat df = new DecimalFormat("0.000");
                Long num1 = Long.parseLong(progressNum);
                Long num2 = Long.parseLong(epsNum);
                String str2 = "";
                str2 = df.format((double) num1/num2);
                progressWidth = Double.parseDouble(str2);
                progressWidth = Double.parseDouble(df.format(progressWidth*100));
                if (progressWidth > 100)
                {
                    progressWidth = 100;
                }
            }


            anime.setMyProgress(myProgress);
            anime.setProgressWidth(progressWidth);
        }
    }

    /**
     * 提取进度数字
     *
     * @return
     */
    private String getNum(String progressNum)
    {
        String str = progressNum.trim();
        if (str.isEmpty())
        {
            return "";
        }
        else if (progressNum.contains("PV"))
        {
            return "100";
        }
        String result = "";
        for(int i=0; i<str.length(); i++)
        {
            if (str.charAt(i)>=48 && str.charAt(i) <= 57)
            {
                result += str.charAt(i);
            }
        }
//        System.out.println(result);
        return result;
    }

    /**
     *  将b站提取的cover下载到服务器中
     *
     */
    private String downloadCoverToServer(String urlString,String filename)throws Exception
    {
        //构造url
        URL url = new URL(urlString);
        //打开连接
        URLConnection connection = url.openConnection();
        //设置请求超时时间
        connection.setConnectTimeout(5*1000);
        //输入流
        InputStream is = connection.getInputStream();

//        //1kb缓冲区
//        byte[] buff = new byte[1024];
//        //读取到的数据长度
//        int len;

//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "BiliBili/";
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
        FileUtil fileUtil = new FileUtil();
        String subCatalog = "BiliBili";

        return fileUtil.uploadFile(is,subCatalog,filename);

    }

}
