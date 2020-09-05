package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.service.MusicService;
import com.Lhan.personal_blog.util.MusicUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicServiceImpl implements MusicService {

    @Override
    @Cacheable(value = "music")
    public Result getPlayList() {
        System.out.println("执行了查询api操作");
        return Result.createWithModels(MusicUtil.getPlayList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"music"},allEntries = true)
    public void refreshMusicCache() {

    }
}
