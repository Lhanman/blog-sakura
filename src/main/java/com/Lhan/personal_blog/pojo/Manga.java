package com.Lhan.personal_blog.pojo;

import java.io.Serializable;

public class Manga implements Serializable {
    private static final long serialVersionUID = -5172532647273106746L;

    private Long id;

    private Long uuid;

    private String cntitle;

    private String jptitle;

    private String entitle;

    private String url;

    private String startdate;

    private String updateat;

    private String enddate;

    private Integer progress;

    private String progressat;

    private String posterimage;

    private String ratingrank;

    private String averagerating;

    private String summary;

    private String status;

    private String mystatus;

    private String chaptercount;

    private String progressStr;

    private String progressWidth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getCntitle() {
        return cntitle;
    }

    public void setCntitle(String cntitle) {
        this.cntitle = cntitle == null ? null : cntitle.trim();
    }

    public String getJptitle() {
        return jptitle;
    }

    public void setJptitle(String jptitle) {
        this.jptitle = jptitle == null ? null : jptitle.trim();
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle == null ? null : entitle.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getUpdateat() {
        return updateat;
    }

    public void setUpdateat(String updateat) {
        this.updateat = updateat == null ? null : updateat.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getProgressat() {
        return progressat;
    }

    public void setProgressat(String progressat) {
        this.progressat = progressat == null ? null : progressat.trim();
    }

    public String getPosterimage() {
        return posterimage;
    }

    public void setPosterimage(String posterimage) {
        this.posterimage = posterimage == null ? null : posterimage.trim();
    }

    public String getRatingrank() {
        return ratingrank;
    }

    public void setRatingrank(String ratingrank) {
        this.ratingrank = ratingrank == null ? null : ratingrank.trim();
    }

    public String getAveragerating() {
        return averagerating;
    }

    public void setAveragerating(String averagerating) {
        this.averagerating = averagerating == null ? null : averagerating.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMystatus() {
        return mystatus;
    }

    public void setMystatus(String mystatus) {
        this.mystatus = mystatus == null ? null : mystatus.trim();
    }

    public String getChaptercount() {
        return chaptercount;
    }

    public void setChaptercount(String chaptercount) {
        this.chaptercount = chaptercount == null ? null : chaptercount.trim();
    }

    public String getProgressStr() {
        return progressStr;
    }

    public void setProgressStr(String progressStr) {
        this.progressStr = progressStr == null ? null : progressStr.trim();
    }

    public String getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(String progressWidth) {
        this.progressWidth = progressWidth == null ? null : progressWidth.trim();
    }
}