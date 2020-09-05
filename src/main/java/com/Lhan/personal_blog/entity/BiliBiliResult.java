package com.Lhan.personal_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BiliBiliResult {

    private Integer code;
    private String message;
    private Integer ttl;
    private AnimeData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public AnimeData getData() {
        return data;
    }

    public void setData(AnimeData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BiliBiliResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", ttl=" + ttl +
                ", data=" + data +
                '}';
    }
}


