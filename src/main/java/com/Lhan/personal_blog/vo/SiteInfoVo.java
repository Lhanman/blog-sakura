package com.Lhan.personal_blog.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SiteInfoVo {

    private String browser_name;

    private String browser_version;

    private String browser_manufacturer;

    private String browser_icon;

    private String userAgent;

    private String operatingSystem;

    private String operationIcon;

    private String articleNum;

    private String commentNum;

    private String tagNum;

    private String ip;

    //城市
    private String city;

    //运营商
    private String area;

    //网站累计PV
    private String PvNum;

    //网站实时访客数
    private String onlineNum;

    //网站今天的访客量
    private String todayVisitors;


}
