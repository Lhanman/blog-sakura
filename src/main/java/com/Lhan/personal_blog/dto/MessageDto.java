package com.Lhan.personal_blog.dto;

public class MessageDto {

    /**
     * tbl_message基础字段
     */
    private int id;

    private String content;

    private String createBy;

    private String email;

    private String name;

    private String ip;

    private boolean is_replied;

    public boolean isIs_replied() {
        return is_replied;
    }

    public void setIs_replied(boolean is_replied) {
        this.is_replied = is_replied;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
