package com.Lhan.personal_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeData {
    private List<Anime> list;
    private Integer pn;
    private Integer ps;
    private Integer total;


    public List<Anime> getList() {
        return list;
    }

    public void setList(List<Anime> list) {
        this.list = list;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AnimeData{" +
                "list=" + list +
                ", pn=" + pn +
                ", ps=" + ps +
                ", total=" + total +
                '}';
    }
}
