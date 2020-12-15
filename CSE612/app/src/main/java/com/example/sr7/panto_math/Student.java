package com.example.sr7.panto_math;

public class Student {
    String name,institution,clas,group,uid,address,mobile;
    double lat,lan;
    public Student(){}

    public Student(String name, String institution, String clas, String group, String uid, String address, String mobile, double lat, double lan) {
        this.name = name;
        this.institution = institution;
        this.clas = clas;
        this.group = group;
        this.uid = uid;
        this.address = address;
        this.mobile = mobile;
        this.lat = lat;
        this.lan = lan;
    }

    public String getName() {
        return name;
    }

    public String getInstitution() {
        return institution;
    }

    public String getClas() {
        return clas;
    }

    public String getGroup() {
        return group;
    }

    public String getUid() {
        return uid;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public double getLat() {
        return lat;
    }

    public double getLan() {
        return lan;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
