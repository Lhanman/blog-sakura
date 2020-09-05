package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.constant.CodeType;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 业务层数据封装
 *
 * @param <T>
 */

public class DataMap<T> extends LinkedHashMap implements Serializable,Cloneable{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private Boolean success;

    private T data;

    private DataMap()
    {

    }

    public static <T> DataMap<T> success()
    {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.success = true;
        dataMap.code = CodeType.SUCCESS_STATUS.getCode();
        return dataMap;
    }

    public static <T> DataMap<T> success(Object code)
    {
        DataMap<T> dataMap = new DataMap<>();
        dataMap.success = true;
        if (code instanceof CodeType)
        {
            dataMap.code = ((CodeType) code).getCode();
            dataMap.message = ((CodeType) code).getMessage();
        }
        else if (code instanceof Integer)
        {
            dataMap.code = (Integer) code;
        }
        return dataMap;
    }

    public static <T> DataMap<T> fail()
    {
        DataMap<T> dataMap = new DataMap<>();
        dataMap.success = false;
        return dataMap;
    }

    public static <T> DataMap<T> fail(T code)
    {
        DataMap<T> dataMap = new DataMap<>();
        dataMap.success = false;
        if (code instanceof CodeType)
        {
            dataMap.code = ((CodeType) code).getCode();
            dataMap.message = ((CodeType) code).getMessage();
        }
        else
        {
            dataMap.code = (Integer) code;
        }
        return dataMap;
    }

    public DataMap message(Object message)
    {
        if (message instanceof CodeType)
        {
            this.message = ((CodeType) message).getMessage();
        }
        else
        {
            this.message = (String) message;
        }
        return this;
    }

    public T getData()
    {
        return data;
    }

    public DataMap<T> setData(T data)
    {
        this.data = data;
        return this;

    }

    public Boolean isSuccess()
    {
        return this.success;
    }

    public Boolean isFail()
    {
        return !this.success;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public void setCode(Integer code)
    {
        this.code = code;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "DataMap{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
