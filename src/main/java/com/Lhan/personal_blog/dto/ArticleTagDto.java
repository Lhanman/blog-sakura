package com.Lhan.personal_blog.dto;

public class ArticleTagDto {

    private String tag_name;

    private Integer tag_size;

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public Integer getTag_size() {
        return tag_size;
    }

    public void setTag_size(Integer tag_size) {
        this.tag_size = tag_size;
    }

    @Override
    public String toString() {
        return "ArticleTagDto{" +
                "tag_name='" + tag_name + '\'' +
                ", tag_size=" + tag_size +
                '}';
    }
}
