package com.kelly.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_ybmb")
public class Tybmb {
    private String id;

    private String ybcode;

    private String ybname;

    private String staus;

    private String cuser;

    private Date cdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYbcode() {
        return ybcode;
    }

    public void setYbcode(String ybcode) {
        this.ybcode = ybcode == null ? null : ybcode.trim();
    }

    public String getYbname() {
        return ybname;
    }

    public void setYbname(String ybname) {
        this.ybname = ybname == null ? null : ybname.trim();
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus == null ? null : staus.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}