package com.Lhan.personal_blog.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class IpUtil {
    private static StringBuilder sb = new StringBuilder();

    public static String getIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
//        System.out.println("this"+ip);
//        if (checkIp(ip))
//        {
//            ip = request.getHeader("Proxy-Client-IP");
//
//
//        }
//
//        if (checkIp(ip))
//        {
//            ip=request.getHeader("WL-Proxy-Client-IP");
//
//        }
//
//        if (checkIp(ip))
//        {
//            ip = request.getRemoteAddr();
//        }
        return ip;
    }

    public static boolean checkIp(String ip)
    {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.split(".").length != 4)
        {
            return true;
        }
        return false;
    }



    /**
     * 从ip字符串形式转换成数组形式
     */
    public static byte[] getIpByteArrayFromString(String ip)
    {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip,"." );
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 字符串数组ip转换成字符串
     */
    public static String getIpStringFromBytes(byte[] ip)
    {
        sb.delete(0,sb.length());
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * 根据某种编码方式将字符数组转换成字符串
     */
    public static String getString(byte[] b,int offset,int len,String encoding)
    {
        try {
            return new String(b,offset,len,encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            return new String(b,offset,len);
        }
    }
}
