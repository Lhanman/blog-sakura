package com.Lhan.personal_blog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatUtil {
    /**
     * 格式化date
     */
    public static String getDateString(Date date)
    {
        String formatDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = formatter.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return formatDate;
    }


    public static Date dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = java.sql.Date.valueOf(sdf.format(date));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return newDate;
    }

    public static String getDateStringForComment()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(formatter);
    }

    /**
     * 获得当前的时间戳
     *
     * @return
     */
    public Long getLongTime()
    {
        Date now = new Date();
        return now.getTime()/1000;
    }


    /**
     * 格式化日期，返回字符串年-月-日 时：分：秒
     */
    public String getFormatDateForSix()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * String转换成LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByString(String date)
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(date,df);
    }

    /**
     * String类型转换成时间戳
     */
    public static Long getTimeStampByString(String date)
    {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d = format.parse(date);
            return d.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
