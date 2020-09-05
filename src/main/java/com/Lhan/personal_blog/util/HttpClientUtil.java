package com.Lhan.personal_blog.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 *
 */
public class HttpClientUtil {

    public static String doGet(String url, Map<String,String> param,String cookie)
    {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            //创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null)
            {
                for (String key : param.keySet())
                {
                    builder.addParameter(key,param.get(key));
                }
            }
            URI uri = builder.build();
            //创建http GET对象
            HttpGet httpGet = new HttpGet(uri);

            //添加请求头信息
            httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.87 Safari/537.36");
            httpGet.addHeader("Accept","*/*");
            httpGet.addHeader("Referer","https://space.bilibili.com/"+param.get("vmid")+"/bangumi");
            httpGet.addHeader("Origin","https://space.bilibili.com");
            httpGet.addHeader("Sec-Fetch-Mode","cors");
            httpGet.addHeader(new BasicHeader("Cookie",cookie));
            //执行请求
            httpResponse = httpClient.execute(httpGet);
            //判断返回状态是否为200
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
                resultString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
        }
        catch (Exception e)
        {
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

    /**
     * 无请求参数的Get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url,String cookie)
    {
        return doGet(url,null,cookie);
    }

    /**
     * Post请求
     */
    public static String doPost(String url,Map<String,String> param)
    {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //创建Http POST请求
            HttpPost httpPost = new HttpPost();
            //创建参数列表
            if (param != null)
            {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet())
                {
                    paramList.add(new BasicNameValuePair(key,param.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                response.close();
                httpClient.close();
            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url,String json)
    {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString="";
        try {
            //创建Http POST对象
            HttpPost httpPost = new HttpPost(url);
            //创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                response.close();
                httpClient.close();
            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
        }
        return resultString;
    }

    public static JSONObject doPostReJson(String url,Map<String,Object> param)
    {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject json = null;
        try {
            //创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //创建参数列表
            if (param != null)
            {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet())
                {
                    paramList.add(new BasicNameValuePair(key,String.valueOf(param.get(key))));
                }

                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            //执行post请求
            response = httpClient.execute(httpPost);
            HttpEntity responseContent = response.getEntity();
            json = JSONObject.fromObject(EntityUtils.toString(responseContent,"utf-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                response.close();
                httpClient.close();
            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
        }
        return json;
    }

}
