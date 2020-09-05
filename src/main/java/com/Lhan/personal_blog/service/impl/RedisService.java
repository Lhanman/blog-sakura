package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.redis.HashRedisServiceImpl;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class RedisService {

    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Autowired
    HashRedisServiceImpl hashRedisService;

    @Autowired
    UserService userService;


    /**
     * 获得user在redis中用户的未读评论个数
     */
    public DataMap getUserNews(String username)
    {

        Long user_id = userService.findUserIdByUsername(username);
        Integer commentNum = (Integer)stringRedisService.get(String.valueOf(user_id));
        if (commentNum == null)
        {
            return DataMap.success().setData(0);
        }
        else
        {
            return DataMap.success().setData(commentNum);
        }
    }

    /**
     * 已读一条消息时修改redis中的未读消息数
     */
    public void readOneCommentOnRedis(Long user_id)
    {
        Integer commentNum = (Integer)stringRedisService.get(String.valueOf(user_id));
        if (commentNum == null)
        {
            return;
        }
        if (--commentNum == 0)
        {
            stringRedisService.remove(String.valueOf(user_id));
        }
        else
        {
            stringRedisService.stringIncrement(String.valueOf(user_id),-1);
        }
    }

    /**
     * 已读所有消息时修改redis中的未读消息数
     */
    public void readAllCommentOnRedis(Long user_id)
    {
        stringRedisService.remove(String.valueOf(user_id));
    }



}
