package com.Lhan.personal_blog.common.cache;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigCache {

    private final static Map<String,String> configCache = new ConcurrentHashMap<>();

    public static void putConfig(String key, String value)
    {
        configCache.put(key,value);
    }

    public static String getConfig(String key)
    {
        return StringUtils.trim(configCache.get(key));
    }
}
