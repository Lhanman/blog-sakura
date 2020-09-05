package com.Lhan.personal_blog.util;


import com.Lhan.personal_blog.vo.MangaVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MangaUtil {


    public static List<MangaVo> getAllMangaVo()
    {

        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        Response response = client.target("https://kitsu.io/api/edge/library-entries?filter[userId]=742686&filter[kind]=manga&page[limit]=500")
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
            manga.setProgressedAt(myDetail.getString("progressedAt")
                                .substring(0,myDetail.getString("progressedAt").indexOf("T")));

            JSONObject detail_link = mangaDetail.getJSONObject("manga").getJSONObject("links");
            String detail_url = detail_link.getString("related");
            Response detailResponse = client.target(detail_url)
                    .request(MediaType.TEXT_PLAIN_TYPE)
                    .header("Content-Type","application/vnd.api+json")
                    .header("Accept","application/vnd.api+json")
                    .get();
            JSONObject detail = JSON.parseObject(detailResponse.readEntity(String.class)).getJSONObject("data").getJSONObject("attributes");
            manga.setStatus(detail.getString("status"));
            manga.setEnTitle(detail.getJSONObject("titles").getString("en_jp"));
            manga.setJaTitle(detail.getJSONObject("titles").getString("ja_jp"));
            manga.setPosterImage(detail.getJSONObject("posterImage").getString("medium"));
            manga.setAverageRating(detail.getString("averageRating"));
            manga.setRatingRank(detail.getString("ratingRank"));

            String summary = detail.getString("synopsis");
            String [] summaryArray = summary.split(" ");
            StringBuilder summaryBuilder = new StringBuilder();
            for (int j=0; j<25; j++)
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

        return mangaList;
    }



}
