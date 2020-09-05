package com.Lhan.personal_blog.component;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Scheduled(cron = "0 0 0 * * ? ")
    @CacheEvict(value = {"music"},allEntries = true)
    public void reloadAnime()
    {
        System.out.println("在凌晨12点执行清空音乐缓存操作");
    }
}
