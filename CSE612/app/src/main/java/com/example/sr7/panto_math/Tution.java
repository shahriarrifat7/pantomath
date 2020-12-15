package com.example.sr7.panto_math;

import java.util.ArrayList;

public class Tution {
    String tid,sid,status,location,mobile,clas,sub,group;
    double lat,lan;
    public Tution(){}
    public Tution(String tid, String sid, String status, String location, String mobile, String clas, String sub, String group, double lat, double lan) {
        this.tid = tid;
        this.sid = sid;
        this.status = status;
        this.location = location;
        this.mobile = mobile;
        this.clas = clas;
        this.sub = sub;
        this.group = group;
        this.lat = 0;
        this.lan = 0;
    }

    public String getTid() {
        return tid;
    }

    public String getSid() {
        return sid;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getMobile() {
        return mobile;
    }

    public String getClas() {
        return clas;
    }

    public String getSub() {
        return sub;
    }

    public String getGroup() {
        return group;
    }

    public double getLat() {
        return lat;
    }

    public double getLan() {
        return lan;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

}
