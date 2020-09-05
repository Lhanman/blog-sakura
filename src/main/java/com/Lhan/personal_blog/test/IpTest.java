package com.Lhan.personal_blog.test;

import com.Lhan.personal_blog.entity.IpLocation;
import com.Lhan.personal_blog.util.IpLocationUtil;

public class IpTest {
    public static void main(String[] args) {
        IpLocationUtil ip = new IpLocationUtil();
        ip.init();
        String ipAddress = "116.22.35.173";
        System.out.println("IP地址["+ipAddress +"]获取到的区域信息为: " +ip.getCountry(ipAddress)
                            +"   ,获取到的城市为:"+ip.getCity(ipAddress) +"    ,运营商:"
                            +ip.getIpLocation(ipAddress).getArea());
        System.out.println(ip.getIpLocation("112.32.23.132"));
        System.out.println(ip.getIpCache());
    }
}
