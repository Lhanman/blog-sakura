package com.Lhan.personal_blog.common.enums;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.constant.ResultConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum  ErrorEnum {

    SUCCESS(ResultConstants.OPERATION_SUCCESS,ResultConstants.SUCCESS_MESSAGE,""),
    ERROR(ResultConstants.OPERATION_ERROR,ResultConstants.ERROR_MESSAGE,""),
    DATA_NOT_EXIST("00002","该数据不存在","data not exist"),
    LOGIN_DISABLE("00004", "账户已被禁用,请联系管理员解除限制!", ""),
    LOGIN_ERROR("00005", "用户名或密码错误！", ""),
    ACCESS_NO_PRIVILEGE("00006", "不具备访问权限", ""),
    PARAM_INCORRECT("00007", "传入参数有误", ""),
    INVALID_TOKEN("00008", "token解析失败", ""),
    REGISTER_ADMIN("00009", "注册失败", ""),
    ACCOUNT_EXIST("00010", "账号已存在", ""),
    ACCOUNT_NOT_EXIST("00011", "用户不存在", ""),
    PASSWORD_ERROR("00012", "密码错误", ""),
    SYNC_POSTS_ERROR("00013", "同步文章失败", ""),
    UPDATE_PASSWORD_ERROR("00014", "密码修改失败", ""),
    FILE_TYPE_ERROR("00015", "文件类型错误", ""),
    IMPORT_FILE_ERROR("00016", "文件导入失败", ""),
    DATABASE_SQL_PARSE_ERROR("00017", "数据库解析异常", ""),
    PHONE_NUMBER_ERROR("00018","手机号码格式错误","phone error"),
    AUTH_CODE_ERROR("00019","验证码输入错误","authCode is error!"),
    NEED_TO_LOGIN("00020","此功能需要登录","need to login"),
    AUTH_CODE_EXPIRE("00021","验证码已过期啦!","auth code is out of date"),
    FILE_IS_EMPTY("00022","文件还有没有上传","file is not be upload"),
    USERNAME_FORMAT_ERROR("00023","用户名长度过长或格式不正确","username format is wrong"),
    USERNAME_IS_NULL("00024","用户名为空","username is blank"),
    USERNAME_UPDATE_SUCCESS("00025","用户信息修改成功","update success");




    private final static Map<String,ErrorEnum> errorEnumMap = new HashMap<>();

    static
    {
        for (ErrorEnum errorEnum : ErrorEnum.values())
        {
            errorEnumMap.put(errorEnum.code,errorEnum);
        }
    }

    private final String code;
    private final String zhMsg;
    private final String enMsg;

    ErrorEnum(String code, String zhMsg, String enMsg) {
        this.code = code;
        this.zhMsg = zhMsg;
        this.enMsg = enMsg;
    }

    public static String getMsg(@NotBlank String code)
    {
//        if (SystemLanguageEnum.EN.getCode().equalsIgnoreCase(Locale.getDefault().getLanguage()))
//        {
//            return errorEnumMap.get(code).enMsg;
//        }
//        else
//        {
            return errorEnumMap.get(code).zhMsg;
//        }
    }
    public static ErrorEnum getErrorEnumMap(@NotBlank String code) {
        return errorEnumMap.get(code);
    }

    public String getCode() {
        return code;
    }

    public String getZhMsg() {
        return zhMsg;
    }

    public String getEnMsg() {
        return enMsg;
    }
}
