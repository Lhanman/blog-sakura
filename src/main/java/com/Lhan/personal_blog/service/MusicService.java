package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;

public interface MusicService {
    Result getPlayList();

    void refreshMusicCache();
}
