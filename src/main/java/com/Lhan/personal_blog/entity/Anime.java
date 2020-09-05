package com.Lhan.personal_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Anime {

    /**
     * 番剧id
     *
     */
    private Long season_id;

    /**
     * 番剧标题
     */
    private String title;

    /**
     * 番剧首页图片
     *
     */
    private String cover;

    /**
     * 番剧简介
     *
     */
    private String evaluate;

    /**
     * 番剧进度
     *
     */
    private String progress;

    /**
     * 番剧地址
     */
    private String url;

    /**
     * 番剧是否完结
     */
    private String is_finish;

    /**
     * 番剧有多少话
     */
    private String total_count;


    /**
     * 番剧最新章节
     *
     */
    private AnimeNew_ep new_ep;

    /**
     * 番剧首播日期
     */
    private AnimePublishDate publish;


    /**
     * 经过处理的progress
     */
    private String myProgress;

    /**
     * 设置前端进度条
     */
    private Double progressWidth;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(Long season_id) {
        this.season_id = season_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(String is_finish) {
        this.is_finish = is_finish;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public AnimeNew_ep getNew_ep() {
        return new_ep;
    }

    public void setNew_ep(AnimeNew_ep new_ep) {
        this.new_ep = new_ep;
    }

    public AnimePublishDate getPublish() {
        return publish;
    }

    public void setPublish(AnimePublishDate publish) {
        this.publish = publish;
    }

    public String getMyProgress() {
        return myProgress;
    }

    public void setMyProgress(String myProgress) {
        this.myProgress = myProgress;
    }

    public Double getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(Double progressWidth) {
        this.progressWidth = progressWidth;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "season_id=" + season_id +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", evaluate='" + evaluate + '\'' +
                ", progress='" + progress + '\'' +
                ", url='" + url + '\'' +
                ", is_finish='" + is_finish + '\'' +
                ", total_count='" + total_count + '\'' +
                '}';
    }
}
