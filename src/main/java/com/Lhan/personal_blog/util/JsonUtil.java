package com.Lhan.personal_blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * json与pojo相互转换的工具类
 *
 */
public class JsonUtil {
    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     *
     */
    public static String objectToJson(Object data)
    {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转换成对象
     */
    public static <T> T jsonToPoJO(String jsonData,Class<T> beanType)
    {
        try {
            T t = MAPPER.readValue(jsonData,beanType);
            return t;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo的list对象
     */
    public static <T> T jsonToList(String jsonData,TypeReference<T> typeReference)
    {
        try {
            return MAPPER.readValue(jsonData,typeReference);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
