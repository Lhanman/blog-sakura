package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.Manga;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.MangaVo;

import java.util.List;

public interface MangaService {

    Result getMangaListRedis(String flag) throws Exception;

    int findMangaNum();

    DataMap findMangaListByPage(int pageNum, int pageSize);

    Manga findMangaByMangaId(Long manga_id);

    void updateManga(MangaVo mangaVo);

    void refreshCache();

}
