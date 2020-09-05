package com.Lhan.personal_blog.test;



import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserAgentTest {

    public static void main(String[] args) {

        String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edge/83.0.478.37";


        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        System.out.println("浏览器id：" + browser.getVersion(ua));
        System.out.println("操作系统：" + operatingSystem.getName());
        System.out.println("浏览器名：" + browser.getName());
        System.out.println(browser.getGroup().getName());



    }


    public static Map getBrowser(HttpServletRequest request)
    {
        Map<String,String> map = new HashMap<>();
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);

        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        System.out.println("浏览器："+browser.getName());
        System.out.println("浏览器id:" + browser.getId());
        System.out.println("浏览器版本"+browser.getVersion(agent));
        System.out.println("操作系统:"+operatingSystem.getName());

        map.put("browser", browser.getName());
        map.put("operatingSystem", operatingSystem.getName());
        return map;
    }

    //匹配浏览器类型
    public static Integer[] getBrowserAndSystem(String agent)
    {
        /*
           1：谷歌浏览器
           2：火狐浏览器
           3：QQ浏览器
           4：360浏览器
           5：IE浏览器
           6：其他浏览器
           1: Android
           2: Windows
           3: IOS
           4: MAC OS
           5： 其他
         */

        Integer browser_obj = null;
        Integer system_obj = null;
        Integer[] temp = new Integer[2];

        //浏览器匹配
        System.out.println("agent参数为"+agent);
        if (agent.indexOf("qqbrowser") > -1)
        {
            System.out.println("qq浏览器匹配成功");
            temp[0] = 3;
        }

        else if (agent.indexOf("chrome") > -1)
        {
            System.out.println("谷歌浏览器匹配成功");
            temp[0] = 1;

        }
        else if (agent.indexOf("trident") > -1)
        {
            System.out.println("ie匹配成功");
            temp[0] = 5;
        }
        else if (agent.indexOf("firefox") > -1)
        {
            System.out.println("火狐匹配成功");
            temp[0] = 2;

        }
        else
        {
            System.out.println("其他浏览器");
        }

        //操作系统匹配
        if (agent.indexOf("android") > -1) {
            temp[1] = 1;
            System.out.println("安卓系统匹配成功");
        }
        else if (agent.indexOf("windows") > -1)
        {
            temp[1] = 2;
            System.out.println("window匹配成功");
        }

        else if (agent.indexOf("mac os") > -1) {
            if (agent.indexOf("iphone os") > -1) {
                temp[1] = 4;
                System.out.println("匹配ios成功");
            }
            else
            {
                temp[1] = 3;
                System.out.println("匹配mac os系统成功");
            }

        }
        else
        {
            temp[1] = 5;
            System.out.println("匹配失败");
        }

        return temp;
    }

}
