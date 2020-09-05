package com.Lhan.personal_blog.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度统计数据获取工具类
 */
@Component
public class BaiduTJUtil {

    private static final String GET_DATA = "https://openapi.baidu.com/rest/2.0/tongji/report/getData";

    private static final String REFRESH_TOKEN_URL = "http://openapi.baidu.com/oauth/2.0/token";

    @Value("${bdTongJi_accessToken}")
    private String access_token;

    @Value("${bdTongJi_refreshToken}")
    private String refresh_token;

    @Value("${bdTongJi_clientId}")
    private String client_id;

    @Value("${bdTongJi_clientSecret}")
    private String client_secret;

    @Value("${bdTongJi_siteId}")
    private String site_id;

    @Value("${bdTongJi_startDate}")
    private String start_date;

    @Value("${bdTongJi_endDate}")
    private String end_date;


    public String updateAccessToken()
    {
        HashMap<String,String> param = new HashMap<>();
        param.put("grant_type","refresh_token");
        param.put("refresh_token",refresh_token);
        param.put("client_id",client_id);
        param.put("client_secret",client_secret);
        System.out.println("执行刷新access_token请求");
        String result = doGet(REFRESH_TOKEN_URL,param);
        return JSON.parseObject(result).getString("access_token");
    }

    private String doGet(String url, Map<String,String> param) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            //创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            //创建http GET对象
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.87 Safari/537.36");
            httpGet.addHeader("Accept","*/*");
            httpGet.addHeader("Sec-Fetch-Mode","cors");
            //执行请求
            httpResponse = httpClient.execute(httpGet);
            //判断返回状态是否为200
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
                resultString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (httpResponse != null)
                {
                    httpResponse.close();
                }
                httpClient.close();
            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
        }
        return resultString;
    }

    public String getPVTotal()
    {
        HashMap<String,String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("site_id",site_id);
        param.put("method","trend/time/a");
        param.put("start_date",start_date);
        param.put("end_date",end_date);
        param.put("metrics","pv_count");
        String result = doGet(GET_DATA,param);
        return JSON.parseObject(result).getJSONObject("result").getJSONArray("sum").getJSONArray(0).getString(0);
    }

    public String getRealTimeVisitorNum()
    {
        HashMap<String,String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("site_id",site_id);
        param.put("method","trend/latest/a");
        param.put("metrics","visit_time");
        param.put("area","china");
        String result = doGet(GET_DATA,param);
        JSONArray AllVisitor = JSON.parseObject(result).getJSONObject("result")
                .getJSONArray("items").getJSONArray(1);
        int onlineNum = 0;
        for (int i=0; i<AllVisitor.size(); i++)
        {

            if (AllVisitor.getJSONArray(i).getString(0).equals("正在访问"))
            {
                onlineNum++;
            }
        }
        return String.valueOf(onlineNum);
    }


    public String getTodayVisitors()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date current =new Date();
        try {
            Date current_format = format.parse(format.format(current));
            long nowTime = current_format.getTime();

            HashMap<String,String> param = new HashMap<>();
            param.put("access_token",access_token);
            param.put("site_id",site_id);
            param.put("method","trend/latest/a");
            param.put("metrics","start_time");
            param.put("area","china");
            String result = doGet(GET_DATA,param);
            JSONArray AllVisitor = JSON.parseObject(result).getJSONObject("result")
                    .getJSONArray("items").getJSONArray(1);
            String time ;
            int todayVisitors = 0;
            for (int i=0; i<AllVisitor.size(); i++)
            {
                time = AllVisitor.getJSONArray(i).getString(0);
                if (isToday(format.parse(time).getTime(),nowTime))
                {
                    todayVisitors++;
                }
            }
            return String.valueOf(todayVisitors);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isToday(long time,long now)
    {
        long day = (now - time) / (24 * 60 * 60 * 1000);
        if (day < 1)
        {

            return true;
        }
        else {
            return false;
        }
    }



}
