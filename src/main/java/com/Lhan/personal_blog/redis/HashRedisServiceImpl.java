package com.Lhan.personal_blog.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class HashRedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 单个设置hash键值
     * @param key
     * @param field
     * @param hv
     */
    public void put(String key,Object field,Object hv)
    {
        HashOperations<String,Object,Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key,field,hv);
    }


    /**
     * 通过实体类反射方式设置key和value
     * @param key
     * @param value
     * @param entityClass
     */
    public void put(String key,Object value,Class<?> entityClass)
    {
        HashOperations<String,Object,Object> hashOperations = redisTemplate.opsForHash();
        //通过反射获得对象属性
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            //保存对象属性和值到对应的key中
            try {
                hashOperations.put(key,field.getName(),field.get(value));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 仅当存在field时才进行设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public Boolean hashPutIfAbsent(String key,Object hashKey,Object value)
    {
        return redisTemplate.opsForHash().putIfAbsent(key,hashKey,value);

    }


    /**
     * 获得对应key值下的属性field的值
     *
     * @param key
     * @param field
     * @return
     */
    public Object get(String key,Object field)
    {
        HashOperations<String,Object,Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key,field);
    }

    /**
     * 获得key中所有值
     * @param key
     * @return
     */
    public Object getAllFieldAndValue(String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * 通过实体类删除指定key下的所有字段
     *
     */
    public void hashDelete(String key,Class<?> entityClass)
    {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields)
        {
            redisTemplate.opsForHash().delete(key,field.getName());
        }
    }

    /**
     * 删除指定key下的指定field
     *
     */

    public void hashDelete(String key,Object field)
    {
        redisTemplate.opsForHash().delete(key,field);

    }

    /**
     * key的指定字段的整数值增加上增量increment
     *
     */
    public Long hashIncrement(String key,Object field,long increment)
    {
        return redisTemplate.opsForHash().increment(key,field,increment);

    }


    /**
     * 判断是否存在键值
     * @param key
     * @return
     */
    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.opsForHash().getOperations().hasKey(key);
    }

    /**
     * 判断key下的field是否存在
     */

    public boolean hasHashKey(String key, Object field)
    {
        return redisTemplate.opsForHash().hasKey(key,field);

    }


}
