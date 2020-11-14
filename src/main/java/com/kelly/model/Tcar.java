package com.kelly.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_car")
public class Tcar {
    private String id;

    private String username;

    private String goodid;

    private String goodname;

    private Double goodprice;

    private Integer goodnum;

    private String payed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getGoodid() {
        return goodid;
    }

    public void setGoodid(String goodid) {
        this.goodid = goodid == null ? null : goodid.trim();
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname == null ? null : goodname.trim();
    }

    public Double getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(Double goodprice) {
        this.goodprice = goodprice;
    }

    public Integer getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(Integer goodnum) {
        this.goodnum = goodnum;
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed == null ? null : payed.trim();
    }
}