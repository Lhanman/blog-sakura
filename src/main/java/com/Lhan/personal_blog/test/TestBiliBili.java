package com.Lhan.personal_blog.test;

import com.Lhan.personal_blog.entity.BiliBiliResult;
import com.Lhan.personal_blog.util.HttpClientUtil;
import com.Lhan.personal_blog.util.JsonUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBiliBili {
    public static void main(String[] args) {
        String cookie="LIVE_BUVID=AUTO5215653472929615; sid=5uvtc5l3; CURRENT_FNVAL=16; stardustvideo=1; rpdid=|(umR~Y|~R|k0J'ulYlJR~JRR; fts=1565862017; CURRENT_QUALITY=112; DedeUserID=6443209; DedeUserID__ckMd5=37942c0423c02882; SESSDATA=36f22a7d%2C1602748385%2C29d1e*41; bili_jct=d7ab2e72f3c0befc669f95f21e5626c8";

        Map<String,String> param = new HashMap<>();
        param.put("type","1");
        param.put("follow_status","0");
        param.put("pn","1");
        param.put("ps","15");
        param.put("vmid","6443209");
        param.put("ts","1571057868836");
        String result = HttpClientUtil.doGet("https://api.bilibili.com/x/space/bangumi/follow/list",param,cookie);
        if (result != null && !result.equals(""))
        {
            System.out.println("取到数据了");
        }
        BiliBiliResult biliBiliResult = JsonUtil.jsonToPoJO(result,BiliBiliResult.class);
        System.out.println(biliBiliResult.getData().getList().get(0).getProgress());
        System.out.println("结束");
    }
}
