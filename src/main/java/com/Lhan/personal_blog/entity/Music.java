package com.Lhan.personal_blog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Music implements Serializable {
    private static final long serialVersionUID = -5172532647273106745L;

    private String name;

    private String url;

    private String artist;

    private String cover;

    private String lrc;
}
