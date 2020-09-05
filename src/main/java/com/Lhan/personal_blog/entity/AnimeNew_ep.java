package com.Lhan.personal_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 番剧最新章节
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeNew_ep {

    private String long_title;

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    @Override
    public String toString() {
        return "AnimeNew_ep{" +
                "long_title='" + long_title + '\'' +
                '}';
    }
}
