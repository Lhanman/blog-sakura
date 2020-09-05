package com.Lhan.personal_blog.common.enums;

public enum  SystemLanguageEnum {

    /**
     * 中文
     */
    ZH("zh", "中文"),

    /**
     * 英文
     */
    EN("en", "英文");

    SystemLanguageEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private final String code;
    private final String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
