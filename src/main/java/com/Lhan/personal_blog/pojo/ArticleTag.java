package com.Lhan.personal_blog.pojo;

import java.io.Serializable;
import java.util.Date;

public class ArticleTag implements Serializable {
    private Long id;

    private Long articleId;

    private String tagName;

    private Date createBy;

    private Date modifiedBy;

    private Boolean isEffective;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
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

    public Boolean getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Boolean isEffective) {

        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        return "ArticleTag{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagName='" + tagName + '\'' +
                ", createBy=" + createBy +
                ", modifiedBy=" + modifiedBy +
                ", isEffective=" + isEffective +
                '}';
    }
}