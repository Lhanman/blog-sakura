package com.Lhan.personal_blog.common.base;

import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.ResultConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于向前端返回统一的结果对象
 */

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5172532647273106745L;

    //操作标识码
    private int success = 0;

    //结果编码
    private String resultCode;

    //提示信息
    private String message;

   //结果对象
    private T model;

    //结果集对象
    private List<T> models;

    //分页信息对象
    private PageInfo pageInfo;

    //扩展字段
    private Object extra;

    //禁止空参构造
    private  Result()
    {
    }

    // 通过操作标识及提示信息构建结果对象
    private static <T> Result<T> createWithSuccessFlag(int success)
    {
       Result<T> result = new Result<T>();
       result.setSuccess(success);
       return result;
    }

    public static <T> Result<T> createWithSuccessMessage()
    {
        Result<T> result = createWithSuccessFlag(ResultConstants.YES);
        result.setResultCode(ResultConstants.OPERATION_SUCCESS);
        result.setMessage(ResultConstants.SUCCESS_MESSAGE);
        return result;
    }

    public static <T> Result<T> createWithSuccessMessage(String message)
    {
        Result<T> result = createWithSuccessFlag(ResultConstants.YES);
        result.setResultCode(ResultConstants.OPERATION_SUCCESS);
        result.setMessage(StringUtils.isBlank(message) ? ResultConstants.SUCCESS_MESSAGE : message);
        return result;
    }

    public static <T> Result<T> createWithModel(T model)
    {
        Result<T> result = createWithSuccessFlag(ResultConstants.YES);
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> createWithModel(String message,T model)
    {
        Result<T> result = createWithSuccessMessage(message);
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> createWithModels(String message,List<T> models)
    {
        Result<T> result = createWithSuccessMessage(message);
        result.setModels(models == null ? new ArrayList<>(0) : models);
        return result;
    }

    public static <T> Result<T> createWithModels(List<T> models)
    {
        Result<T> result = createWithSuccessMessage();
        result.setModels(models == null ? new ArrayList<>(0) : models);
        return result;
    }

    public static <T> Result<T> createWithPaging(String message,List<T> models, PageInfo pageInfo)
    {
        Result<T> result = createWithModels(message,models == null ? new ArrayList<>(0) : models);
        result.setPageInfo(pageInfo);
        return result;
    }

    public static <T> Result<T> createWithPaging(List<T> models,PageInfo pageInfo)
    {
        Result<T> result = createWithModels(models == null ? new ArrayList<>(0) : models);
        result.setPageInfo(pageInfo);
        return result;
    }

    public static <T> Result<T> createWithErrorMessage(String message,String errorCode)
    {
        Result<T> result = createWithSuccessFlag(ResultConstants.NO);
        result.setMessage(message);
        result.setResultCode(errorCode);
        return result;
    }

    public static <T> Result<T> createWithErrorMessage(ErrorEnum errorEnum)
    {
        return createWithErrorMessage(ErrorEnum.getMsg(errorEnum.getCode()),errorEnum.getCode());
    }

    public static <T> Result<T> createWithError()
    {
        return createWithErrorMessage(ErrorEnum.ERROR);
    }

}


