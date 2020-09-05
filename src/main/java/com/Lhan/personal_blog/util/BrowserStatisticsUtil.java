package com.Lhan.personal_blog.util;



import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取浏览器信息以及操作系统信息工具类
 *
 */
public class BrowserStatisticsUtil {


    public static Map getBrowserAndOS(HttpServletRequest request)
    {
        Map<String,String> map = new HashMap<>();
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);


        map.put("browser_version", userAgent.getBrowser().getVersion(agent).getVersion());
        map.put("operatingSystem", userAgent.getOperatingSystem().getName());
        map.put("browser_name",userAgent.getBrowser().getName());
        map.put("browser_manufacturer",userAgent.getBrowser().getManufacturer().getName());

        return map;
    }

    public static Map getBrowserAndOSByUA(String user_agent)
    {
        if (user_agent != null && user_agent.length() != 0)
        {

            Map<String,String> map = new HashMap<>();
            UserAgent userAgent = UserAgent.parseUserAgentString(user_agent);
            map.put("operatingSystem", userAgent.getOperatingSystem().getName());
            map.put("browser_name",userAgent.getBrowser().getName());
            map.put("browser_version", userAgent.getBrowser().getVersion(user_agent).getVersion());
            return map;

        }
      return null;
    }
}
