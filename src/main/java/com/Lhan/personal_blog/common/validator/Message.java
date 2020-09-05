package com.Lhan.personal_blog.common.validator;

public interface Message {
    /**
     * 类内部使用，自定义reject value
     */
    String CK_RANGE_MESSAGE_LENGTH_TYPE = "length must be between 0 and 11:%s";
    String CK_NUMERIC_TYPE = "field must be a number:%s";

    /**
     * 注解默认
     */
    String CK_NOT_BLANK_DEFAULT = "cannot be blank";
    String CK_NUMERIC_DEFAULT = "must be a number";
    String CK_RANGE_DEFAULT = "should be an integer, between {min} and {max}";
    String ID_NOT_NULL = "cannot be null";
    String PAGE_NOT_NULL = "page not be null";
    String SIZE_NOT_NULL = "size not be null";


}
