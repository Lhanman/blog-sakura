package com.Lhan.personal_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 番剧首播日期
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimePublishDate {

    private String release_date_show;

    public String getRelease_date_show() {
        return release_date_show;
    }

    public void setRelease_date_show(String release_date_show) {
        this.release_date_show = release_date_show;
    }

    @Override
    public String toString() {
        return "AnimePublishDate{" +
                "release_date_show='" + release_date_show + '\'' +
                '}';
    }
}
