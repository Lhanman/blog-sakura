package com.Lhan.personal_blog.pojo;

public class Config {
    private Integer id;

    private Integer type;

    private String configkey;

    private String configvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getConfigkey() {
        return configkey;
    }

    public void setConfigkey(String configkey) {
        this.configkey = configkey == null ? null : configkey.trim();
    }

    public String getConfigvalue() {
        return configvalue;
    }

    public void setConfigvalue(String configvalue) {
        this.configvalue = configvalue == null ? null : configvalue.trim();
    }
}