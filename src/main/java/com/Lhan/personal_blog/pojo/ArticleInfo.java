package com.Lhan.personal_blog.pojo;

import java.util.Date;

public class ArticleInfo {
    private Long id;

    private String title;

    private String summary;

    private Boolean isTop;

    private Integer traffic;

    private Integer likes;

    private Date createBy;

    private Date modifiedBy;

    private Long lastarticleid;

    private Long nextarticleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    public Date getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Date modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getLastarticleid() {
        return lastarticleid;
    }

    public void setLastarticleid(Long lastarticleid) {
        this.lastarticleid = lastarticleid;
    }

    public Long getNextarticleid() {
        return nextarticleid;
    }

    public void setNextarticleid(Long nextarticleid) {
        this.nextarticleid = nextarticleid;
    }
}