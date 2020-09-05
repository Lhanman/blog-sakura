package com.Lhan.personal_blog.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AnimeVo implements Serializable {

    private static final long serialVersionUID = -5172532647273106745L;

    private Long session_id;

    private String title;

    private String background;

    private String cover;

    private String evaluate;

    private String myProgress;

    private Double progressWidth;

    private String long_title;

    private String release_date_show;

    private String url;


}
