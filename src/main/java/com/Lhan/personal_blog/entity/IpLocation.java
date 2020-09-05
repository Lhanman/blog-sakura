package com.Lhan.personal_blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class IpLocation {

    //国家
    private String country;

    //区域-省份+城市
    private String area;

    public IpLocation()
    {
        country = area = "";
    }

    public synchronized IpLocation getCopy()
    {
        IpLocation ret = new IpLocation();
        ret.country = country;
        ret.area = area;
        return ret;
    }

    public String getCity()
    {
        String city = "";
        if (country != null)
        {
            String[] array = country.split("省");
            if (array != null && array.length > 1)
            {
                city = array[1];
            }
            else
            {
                city = country;
            }
            if(city.length() > 3)
            {
                city.replace("内蒙古","");
            }
        }
        return city;
    }

    public void setArea(String area)
    {
        //如果是局域网，纯真ip数据库会显示CZ88.NET
        if (area.trim().equals("CZ88.NET"))
        {
            this.area = "本机或本地网络";
        }
        else
        {
            this.area = area;
        }
    }




}
