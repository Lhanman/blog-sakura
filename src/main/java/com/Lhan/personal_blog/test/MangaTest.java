package com.Lhan.personal_blog.test;

import com.Lhan.personal_blog.vo.MangaVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class MangaTest {
    public static void main(String[] args) {

        /**
         * 以下是通过jersey的Client对象进行请求
         */

        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        Response response = client.target("https://kitsu.io/api/edge/library-entries?filter[userId]=742686&filter[kind]=manga")
                                .request(MediaType.TEXT_PLAIN_TYPE)
                                .header("Content-Type","application/vnd.api+json")
                                .header("Accept","application/vnd.api+json")
                                .get();
        JSONArray array = JSON.parseObject(response.readEntity(String.class)).getJSONArray("data");

        List<MangaVo> mangaList = new ArrayList<>();
        for (int i=0; i<array.size(); i++)
        {
            JSONObject myDetail = array.getJSONObject(i).getJSONObject("attributes");
            JSONObject mangaDetail = array.getJSONObject(i).getJSONObject("relationships");
            Long uuid = Long.parseLong(array.getJSONObject(i).getString("id"));

            MangaVo manga = new MangaVo();
            manga.setUuid(uuid);
            manga.setNotes(myDetail.getString("notes"));
            manga.setProgress(Integer.parseInt(myDetail.getString("progress")));
            manga.setMyStatus(myDetail.getString("status"));
            manga.setProgressedAt(myDetail.getString("progressedAt"));

            JSONObject detail_link = mangaDetail.getJSONObject("manga").getJSONObject("links");
            String detail_url = detail_link.getString("related");
            Response detailResponse = client.target(detail_url)
                                            .request(MediaType.TEXT_PLAIN_TYPE)
                                            .header("Content-Type","application/vnd.api+json")
                                            .header("Accept","application/vnd.api+json")
                                            .get();
            JSONObject detail = JSON.parseObject(detailResponse.readEntity(String.class)).getJSONObject("data").getJSONObject("attributes");
            manga.setStatus(detail.getString("status"));
            manga.setEnTitle(detail.getJSONObject("titles").getString("en"));
            manga.setJaTitle(detail.getJSONObject("titles").getString("ja_jp"));
            manga.setPosterImage(detail.getJSONObject("posterImage").getString("medium"));
            manga.setAverageRating(detail.getString("averageRating"));
            manga.setRatingRank(detail.getString("ratingRank"));

            String summary = detail.getString("synopsis");
            String [] summaryArray = summary.split(" ");
            StringBuilder summaryBuilder = new StringBuilder();
            for (int j=0; j<50; j++)
            {
                summaryBuilder.append(summaryArray[j]+" ");
            }
            manga.setSummary(summaryBuilder.toString()+"...");
            manga.setStartDate(detail.getString("startDate"));
            manga.setEndDate(detail.getString("endDate"));
            manga.setChapterCount(detail.getString("chapterCount"));
            manga.setUpdateAt(detail.getString("updatedAt"));

            mangaList.add(manga);
        }

        System.out.println(mangaList.get(1).getProgressedAt());



        /**
         * 一下的是通过HttpUrlClient实现请求的方法
         */
//        Map<String,String> params = new HashMap<>();
//        String result;
//        try {
//            URL url = new URL("https://api.jikan.moe/v3/user/Lhanman/mangalist/all");
//            HttpURLConnection connection = null;
//            trustAllHosts();
//            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
//            if (url.getProtocol().toLowerCase().equals("https"))
//            {
//                https.setHostnameVerifier(DO_NOT_VERIFY);
//                connection = https;
//            }
//            else
//            {
//                connection = (HttpURLConnection) url.openConnection();
//            }
//
//            result = getResponse(connection);
//            System.out.println(result);
//            System.out.println("finished");


//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }



    }
    /**
     * 相信所有证书，不检查https证书
     */
    private static void trustAllHosts()
    {
        final String TAG = "trustAllHosts";
        //创建一个trust manager不检验证书链
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        //安装 all-trust manager
        try
        {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,trustAllCerts,new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public static String getResponse(HttpURLConnection connection)
    {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            //设置属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");

            //开启连接
            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (br != null)
            {
                try {
                    br.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {

                connection.disconnect();
            }
        }
        return sb.toString();
    }
}
