package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.mapper.MangaMapper;
import com.Lhan.personal_blog.pojo.Manga;
import com.Lhan.personal_blog.pojo.MangaExample;
import com.Lhan.personal_blog.service.MangaService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.FileUtil;
import com.Lhan.personal_blog.util.MangaUtil;
import com.Lhan.personal_blog.vo.FriendshipLinkVo;
import com.Lhan.personal_blog.vo.MangaVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MangaServiceImpl implements MangaService {

    @Resource
    MangaMapper mangaMapper;

    @Override
    @Cacheable(value = "manga",key = "'manga'")
    public Result getMangaListRedis(String flag) throws Exception {
        List<Manga> mangaList = new ArrayList<>();
        //当更新mysql数据删除缓存时设置flag为updateByDataBase，直接更新redis中的数据，不从api中取出数据，而只是从mysql中取出数据,
        //此操作只是为了优化代码执行时间
        if (!flag.equals("updateByDataBase")) {
            System.out.println("请求了api");
            List<MangaVo> mangaVoList = MangaUtil.getAllMangaVo();

            //增加了进度条字段，和进度字符串字段,这里处理不浪费时间就不优化了
            handlerProgressFormat(mangaVoList);
            handlerMyProgress(mangaVoList);


            for (MangaVo mangaVo : mangaVoList) {

                MangaExample example = new MangaExample();
                example.or().andUuidEqualTo(mangaVo.getUuid());
                //判断是否存在新的漫画
                if (mangaMapper.selectByExample(example).size() == 0)
                {
                    //将图片下载至阿里云oss
                    String fileUrl = downloadCoverToServer(mangaVo.getPosterImage(),mangaVo.getUuid()+".jpg");
                    mangaVo.setPosterImage(fileUrl);
                }
                else
                {
                    Manga manga = mangaMapper.selectByExample(example).get(0);
                    mangaVo.setPosterImage(manga.getPosterimage());
                }

                Manga manga = new Manga();
                manga.setUuid(mangaVo.getUuid());
                manga.setEntitle(mangaVo.getEnTitle());
                manga.setJptitle(mangaVo.getJaTitle());
                manga.setStartdate(mangaVo.getStartDate());
                manga.setEnddate(mangaVo.getEndDate());
                manga.setUpdateat(mangaVo.getUpdateAt());
                if (!mangaVo.getNotes() .equals(""))
                {
                    manga.setSummary(mangaVo.getNotes());
                }
                else
                {

                    manga.setSummary(mangaVo.getSummary());
                }
                manga.setProgress(mangaVo.getProgress());
                manga.setProgressat(mangaVo.getProgressedAt());
                manga.setProgressStr(mangaVo.getProgressStr());
                manga.setProgressWidth(String.valueOf(mangaVo.getProgressWidth()));
                manga.setPosterimage(mangaVo.getPosterImage());
                manga.setRatingrank(mangaVo.getRatingRank());
                manga.setAveragerating(mangaVo.getAverageRating());
                manga.setStatus(mangaVo.getStatus());
                manga.setMystatus(mangaVo.getMyStatus());
                manga.setChaptercount(mangaVo.getChapterCount());

                if (mangaVo.getUuid() == null) {
                    Result.createWithErrorMessage(ErrorEnum.PARAM_INCORRECT);
                }

                if (mangaMapper.selectByExample(example).size() == 0) {
                    System.out.println("执行插入操作 "+manga.getJptitle());
                    mangaMapper.insert(manga);
                } else {
                    Manga updateManga = mangaMapper.selectByExample(example).get(0);
                    manga.setId(updateManga.getId());
                    mangaMapper.updateByPrimaryKeySelective(manga);
                }
            }
        }
        MangaExample example2 = new MangaExample();
        example2.or();
        mangaList = mangaMapper.selectByExample(example2);

        return Result.createWithModels(mangaList);
    }

    @Override
    public int findMangaNum() {
        MangaExample example = new MangaExample();
        example.or();
        return mangaMapper.selectByExample(example).size();
    }

    @Override
    public DataMap findMangaListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<MangaVo> mangaVoList = new ArrayList<>();

        MangaExample example = new MangaExample();
        example.or();
        List<Manga> mangaList = mangaMapper.selectByExample(example);


        for (Manga manga : mangaList)
        {
            MangaVo mangaVo = new MangaVo();
            mangaVo.setId(manga.getId());
            mangaVo.setUuid(manga.getUuid());
            mangaVo.setCnTitle(manga.getCntitle());
            mangaVo.setJaTitle(manga.getJptitle());
            mangaVo.setStartDate(manga.getStartdate());
            mangaVo.setMyStatus(manga.getMystatus());
            mangaVo.setAverageRating(manga.getAveragerating());
            mangaVo.setProgressStr(manga.getProgressStr());
            mangaVo.setEndDate(manga.getEnddate());
            mangaVo.setUrl(manga.getUrl());
            mangaVoList.add(mangaVo);
        }
        JSONArray mangaArray = JSONArray.fromObject(mangaVoList);
        return DataMap.success().setData(mangaArray);
    }

    @Override
    public Manga findMangaByMangaId(Long manga_id) {
        return mangaMapper.selectByPrimaryKey(manga_id);
    }

    @Override
    @Transactional
    public void updateManga(MangaVo mangaVo) {
        Manga manga = v2p(mangaVo);
        mangaMapper.updateByPrimaryKeySelective(manga);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"manga"},allEntries = true)
    public void refreshCache() {

    }


    /**
     * 修改myStatus字段的String格式
     */

    private void handlerMyProgress(List<MangaVo> mangaVoList)
    {
        for (MangaVo mangaVo : mangaVoList)
        {
            if (mangaVo.getMyStatus().equals("current"))
            {
                mangaVo.setMyStatus("正在追漫");
            }
            else if (mangaVo.getMyStatus().equals("completed"))
            {
                mangaVo.setMyStatus("已看完");
            }
            else if (mangaVo.getMyStatus().equals("planned"))
            {
                mangaVo.setMyStatus("想看");
            }
            else if (mangaVo.getMyStatus().equals("on_hold"))
            {
                mangaVo.setMyStatus("暂时不看");
            }
            else
            {
                mangaVo.setMyStatus("烂漫");
            }
        }
    }



    /**
     * 修改追漫进度返回String格式
     *
     */
    private void handlerProgressFormat(List<MangaVo> mangaVoList)
    {

        for (MangaVo mangaVo : mangaVoList)
        {
            String epsNum = "未知";



            String myProgress = "";
            //获取处理后的progress
            if (mangaVo.getStatus().equals("finished"))
            {
                myProgress = "看到第" +mangaVo.getProgress() + "话,共 "+ mangaVo.getChapterCount() + " 话";
            }
            else if (mangaVo.getMyStatus().equals("completed"))
            {
                myProgress = "看到第" +mangaVo.getProgress() + "话,已看完";
            }
            else if (mangaVo.getStatus().equals("upcoming") || mangaVo.getStatus().equals("unreleased") || mangaVo.getStatus().equals("tba") )
            {
                myProgress = "该漫画还没上架，尽情期待!";
            }
            else
            {
                myProgress = "看到第" +mangaVo.getProgress() + "话, 未完结";
            }


            //获取进度条
            double  progressWidth = 0;
            if (mangaVo.getStatus().equals("current"))
            {
                if (mangaVo.getMyStatus().equals("completed"))
                {
                    progressWidth = 100;
                }
                else
                {
                    progressWidth = 50;
                }
            }
            else if (mangaVo.getStatus().equals("upcoming") || mangaVo.getStatus().equals("unreleased") || mangaVo.getStatus().equals("tba") )
            {
                progressWidth = 0;
            }
            else
            {
                DecimalFormat df = new DecimalFormat("0.000");
                Long num1 = Long.parseLong(""+mangaVo.getProgress());
                Long num2 = Long.parseLong(mangaVo.getChapterCount());
                String str2 = "";
                str2 = df.format((double) num1/num2);
                progressWidth = Double.parseDouble(str2);
                progressWidth = Double.parseDouble(df.format(progressWidth*100));
                if (progressWidth > 100)
                {
                    progressWidth = 100;
                }
            }


            mangaVo.setProgressStr(myProgress);
            mangaVo.setProgressWidth(progressWidth);
        }
    }

    /**
     *  将KitSu提取的cover下载到服务器中
     *
     */
    private String downloadCoverToServer(String urlString,String filename)throws Exception
    {
        //构造url
        URL url = new URL(urlString);
        //打开连接
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //设置请求超时时间
        connection.setConnectTimeout(5*1000);
        //输入流
        InputStream is = connection.getInputStream();

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
        FileUtil fileUtil = new FileUtil();
        String subCatalog = "Manga";
        return fileUtil.uploadFile(is,subCatalog,filename);

    }

    private Manga v2p(MangaVo mangaVo)
    {
        Manga manga = new Manga();
        manga.setId(mangaVo.getId());
        manga.setCntitle(mangaVo.getCnTitle().trim());
        manga.setUrl(mangaVo.getUrl().trim());
        return manga;
    }


}
