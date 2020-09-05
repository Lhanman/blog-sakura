package com.Lhan.personal_blog.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * String类型redis操作
 *
 */
@Service
public class StringRedisServiceImpl implements RedisService{

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置key和value，超时时间-1
     */
    public void set(String key,Object value)
    {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * 设置带有超时时间的key和value
     *
     */
    public void set(String key,Object value,long timeout)
    {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,timeout);
    }

    /**
     * 获得key值
     *
     */
    public Object get(String key)
    {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 获得key超时时间
     *
     */
    public long getExpire(String key)
    {
        return redisTemplate.getExpire(key);

    }

    /**
     *  设置key的超时时间（秒）
     *
     */
    public Boolean expire(String key,long timeout)
    {
        return redisTemplate.expire(key,timeout, TimeUnit.SECONDS);

    }

    /**
     * 删除key，value
     *
     */

    public void remove(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * key的指定字段的整数值增加上增量increment
     *
     */
    public Long stringIncrement(String key,long increment)
    {
        return redisTemplate.opsForValue().increment(key,increment);
    }

    /**
     * 判断key是否存在
     *
     */
    public Boolean hasKey(String key)
    {
        return redisTemplate.opsForValue().getOperations().hasKey(key);
    }


}
