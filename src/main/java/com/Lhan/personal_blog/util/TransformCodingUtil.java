package com.Lhan.personal_blog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 中文转码工具类
 */
public class TransformCodingUtil {

    /**
     * 中文转unicode编码
     *
     */
    public static String stringToUnicode(final String gbString)
    {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = StringUtil.BLANK;
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * unicode编码转中文
     *
     */
    public static String unicodeToString(String unicode)
    {
        if(!unicode.contains("\\u")){
            return unicode;
        }

        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    /**
     * 将utf-8展开的16进制数转换成utf-8汉字
     * @param strUtf16
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String utf16ToUtf8(String strUtf16) throws UnsupportedEncodingException {
        String strUtf8 = URLDecoder.decode(strUtf16, "UTF-8");
        return strUtf8;
    }

    /**
     * 判断是否为汉字
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
        for(int i=0;i<str.length();i++){
            int char1 = str.charAt(i);
            //汉字范围
            if(char1>=19968 && char1<=171941){
                return true;
            }
        }
        return false;
    }
}
