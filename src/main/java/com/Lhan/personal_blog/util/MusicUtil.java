package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.common.cache.ConfigCache;
import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.entity.Music;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicUtil {

    private static final String PREFIX_URL = "https://v1.hitokoto.cn/nm/playlist/";

    private static final String PLAY_URL = "https://v1.hitokoto.cn/nm/redirect/music/";

    private static final String LYRIC_URL ="https://v1.hitokoto.cn/nm/lyric/";

    private static String getResponse(HttpURLConnection connection)
    {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            //设置属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("cookie","appver=1.5.0.75771");
            connection.setRequestProperty("refer","https://music.163.com/");
            //开启连接
            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
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


    private static List<Music> getAllMusic(JSONArray array)
    {
        List<Music> list = new ArrayList<>();
        for (int i=0; i<array.size(); i++)
        {
            JSONObject object = array.getJSONObject(i);
            Music music = new Music();
            music.setName(object.getString("name"));

            music.setUrl(PLAY_URL+ object.getString("id"));
            music.setLrc(getLyric(object.getString("id")));
            music.setArtist(object.getJSONArray("ar").getJSONObject(0).getString("name"));
            music.setCover(object.getJSONObject("al").getString("picUrl").replaceAll("http://"
                                                                                    ,"https://"));
            list.add(music);
        }
        return list;
    }

    private static String getLyric(String song_id)
    {
        try{
            //发起https请求
            URL url = new URL(MusicUtil.LYRIC_URL+song_id);
            HttpURLConnection connection = null;
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
            if (url.getProtocol().toLowerCase().equals("https"))
            {
                https.setHostnameVerifier(DO_NOT_VERIFY);
                connection = https;
            }
            else
            {
                connection = (HttpURLConnection) url.openConnection();
            }

            String result = MusicUtil.getResponse(connection);

            return JSON.parseObject(result).getJSONObject("lrc").getString("lyric");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Music> getPlayList()
    {
        try {
            //发起http请求，之后修改从ConfigCache取id
            URL url = new URL(MusicUtil.PREFIX_URL+"3115275567");
            HttpURLConnection connection = null;
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
            if (url.getProtocol().toLowerCase().equals("https"))
            {
                https.setHostnameVerifier(DO_NOT_VERIFY);
                connection = https;
            }
            else
            {
                connection = (HttpURLConnection) url.openConnection();
            }

            String result = MusicUtil.getResponse(connection);

            JSONArray array = JSON.parseObject(result).getJSONObject("playlist").getJSONArray("tracks");
            return MusicUtil.getAllMusic(array);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    //获取重定向地址,坑人代码，导致获取歌曲直链过期的原因！！！！
    private static String getRedirectUrl(String path)
    {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(path).openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(2000);
//            connection.setRequestProperty("cookie","_iuqxldmzr_=32; _ntes_nnid=dd1d3637c6c1de22e36047dba938f7e2,1565937874453; _ntes_nuid=dd1d3637c6c1de22e36047dba938f7e2; mail_psc_fingerprint=7702e4a7d5ab60fa557e13f7812a91f5; vinfo_n_f_l_n3=417783dafc27a61b.1.0.1580361804709.0.1580379834304; WM_TID=iLwJkl9%2FFQ5EAEFEQQIuViZM8Op4fth7; usertrack=ezq0ZV6b8+CImjqdDCgOAg==; P_INFO=ubhjkh@163.com|1587806297|1|mail163|00&99|null&null&null#gud&440100#10#0#0|&0|mail163|ubhjkh@163.com; nts_mail_user=ubhjkh@163.com:-1:1; WM_NI=u%2FvxugGs%2FuSeviIWmMdDG%2F%2FKUmaKdDwKeW%2FysLp6p64kFD033PxEm%2BnbMxOzSIi60XTafshx9LUTyCKDhxFn1o9OtOfRqACCeeTe%2Fk%2F148FzA5ye7WNNqZnxYoD3aTRTSWw%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6ee85ae48fcb4fea7c46d8dac8bb6d15b978b9faef54bb6ade589b16b8fb69a96f02af0fea7c3b92ab7f1ba99f86d85ade196d26fb6b787b8f047a89c89d0ae4197b3aface750b595abb8ae73b194c0d7f33ef8b58aa5b339f190b8a6f021aebe8399ce729197a7d7b35498bfaa94eb74fb949bb5fc49899ca899c566f3b1f7b0f745a68ebfa2c249a7bb9c86ae61edeffcd9c57283ebb786b65d8e92bfbbed749bb18cd4e460928c9db8f637e2a3; MUSIC_U=923f090a8ec665f0dd0c3250cd6770a1d62d12c36ac56f6aaf35fe92033c8f1133a649814e309366; __remember_me=true; __csrf=b072a7a2b50214657499d79dfa566180; JSESSIONID-WYYY=nHrmcVVYiP8%2FDIu4qTAk%2FUQMe1Eyffj%2BtgKhn4b9%5CcS%5Cd9nYkjV7vG%2Bt8UD1IY59kpAH8AeYHu5Vq5OIbR6%2Fix%5CuOBlbPm%5CT2ZWwV6Mach1cSxczpIwuQKBfPursvfxbplrtBzvhuZfvwbVc3gQUfPgmYZ1EES6D%2BfIjcYmQAUWEs7JC%3A1589004042637");

            return connection.getHeaderField("Location").replaceFirst("http","https");
        }
        catch (Exception e)
        {
            return "null";
        }
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
}
