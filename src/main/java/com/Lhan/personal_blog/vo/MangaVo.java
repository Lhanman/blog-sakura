package com.Lhan.personal_blog.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MangaVo extends BaseVo<MangaVo> implements Serializable {

    private static final long serialVersionUID = -5172532647273106745L;

    private Long uuid;

    private String enTitle;

    private String jaTitle;

    private String cnTitle;

    private String url;

    private String startDate;

    private String updateAt;

    private String endDate;

    //我的评价
    private String notes;

    private Integer progress;

    private String progressedAt;

    //漫画封面
    private String posterImage;

    //在KitSu的评分排名
    private String ratingRank;

    //综合评分
    private String averageRating;

    //概要
    private String summary;

    //漫画状态
    private String status;

    //我追漫的状态
    private String myStatus;

    //漫画的总集数
    private String chapterCount;

    //漫画进度条百分比
    private Double progressWidth;

    //漫画进度
    private String progressStr;
}
