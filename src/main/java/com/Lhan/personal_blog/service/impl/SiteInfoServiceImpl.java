package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.mapper.ArticleTagMapper;
import com.Lhan.personal_blog.service.ArticleService;
import com.Lhan.personal_blog.service.CommentService;
import com.Lhan.personal_blog.service.SiteInfoService;
import com.Lhan.personal_blog.service.TagService;
import com.Lhan.personal_blog.util.BaiduTJUtil;
import com.Lhan.personal_blog.util.BrowserStatisticsUtil;
import com.Lhan.personal_blog.util.IpLocationUtil;
import com.Lhan.personal_blog.util.IpUtil;
import com.Lhan.personal_blog.vo.SiteInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class SiteInfoServiceImpl implements SiteInfoService {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Resource
    ArticleTagMapper articleTagMapper;

    @Autowired
    BaiduTJUtil baiduTJUtil;

    private static IpLocationUtil ipUtil;

    static {
        ipUtil = new IpLocationUtil();
        ipUtil.init();

    }

    @Override
    public Result getSiteInfoAndUserAgent(HttpServletRequest request) {
        Map browserAndOSInfo = BrowserStatisticsUtil.getBrowserAndOS(request);
        String operateSystem = (String) browserAndOSInfo.get("operatingSystem");
        String browserName = (String) browserAndOSInfo.get("browser_name");
        String ipAddress = IpUtil.getIp(request);
        String city = ipUtil.getIpLocation(ipAddress).getCountry();
        String area = ipUtil.getArea(ipAddress);
        String pvNum = baiduTJUtil.getPVTotal();
        String onlineNum = baiduTJUtil.getRealTimeVisitorNum();
        String todayVisitors = baiduTJUtil.getTodayVisitors();

        SiteInfoVo siteInfoVo = new SiteInfoVo();
        siteInfoVo.setArticleNum(String.valueOf(articleService.findArticleCount()));
        siteInfoVo.setBrowser_name(browserName);
        siteInfoVo.setBrowser_version((String) browserAndOSInfo.get("browser_version"));
        siteInfoVo.setBrowser_manufacturer((String) browserAndOSInfo.get("browser_manufacturer"));
        siteInfoVo.setOperatingSystem(operateSystem);
        siteInfoVo.setUserAgent(request.getHeader("User-Agent"));
        siteInfoVo.setCommentNum(String.valueOf(commentService.commentNum()));
        siteInfoVo.setIp(ipAddress);
        siteInfoVo.setCity(city);
        siteInfoVo.setArea(area);
        siteInfoVo.setTagNum(String.valueOf(articleTagMapper.findTagNameDistinct().size()));
        siteInfoVo.setBrowser_icon(getBrowserIcon(browserName));
        siteInfoVo.setOperationIcon(getOperateIcon(operateSystem));
        siteInfoVo.setPvNum(pvNum);
        siteInfoVo.setOnlineNum(onlineNum);
        siteInfoVo.setTodayVisitors(todayVisitors);


        return Result.createWithModel(siteInfoVo);
    }


    private String getBrowserIcon(String browserName)
    {
        if (browserName.contains("Chrome"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/chrome.svg";
        }
        else if (browserName.contains("QQ"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/QQBrowser.svg";
        }
        else if (browserName.contains("Safari"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/safari.svg";
        }
        else if (browserName.contains("Firefox"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/firefox.svg";
        }
        else if (browserName.contains("Opera"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/opera15.svg";
        }
        else if (browserName.contains("Edge"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/edge.svg";
        }
        else if (browserName.contains("SouGou"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/sogou.svg";
        }
        else
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/unknow.svg";
        }
    }


    private String getOperateIcon(String operateSystem)
    {
        if (operateSystem.contains("Windows 10"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win10.svg";
        }
        else if (operateSystem.contains("Android"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/android.svg";
        }
        else if (operateSystem.contains("Iphone"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }
        else if (operateSystem.contains("Linux"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/linux.svg";
        }

        else if (operateSystem.contains("Windows 7"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win7.svg";
        }

        else if (operateSystem.contains("Mac"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }

        else if (operateSystem.contains("Windows Server 2003"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/windows_win7.svg";
        }

        else if (operateSystem.contains("iPad"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/iphone.svg";
        }

        else if (operateSystem.toLowerCase().contains("ubuntu"))
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/ubuntu.svg";
        }
        else
        {
            return "https://cdn.jsdelivr.net/gh/moezx/cdn@3.4.5/img/Sakura/images/ua/svg/unknow.svg";
        }
    }

}
