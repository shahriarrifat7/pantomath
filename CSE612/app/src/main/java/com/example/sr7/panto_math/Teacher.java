package com.example.sr7.panto_math;

import android.os.ParcelUuid;

public class Teacher {
    String name,qualification,foi,address,mobile,institution,gender,uid;
    double lat,lan;

    public Teacher(){

    }

    public Teacher(String name, String qualification, String foi, String address, String mobile, String institution, String gender, String uid, double lat, double lan) {
        this.name = name;
        this.qualification = qualification;
        this.foi = foi;
        this.address = address;
        this.mobile = mobile;
        this.institution = institution;
        this.gender = gender;
        this.uid = uid;
        this.lat = lat;
        this.lan = lan;
    }

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }

    public String getFoi() {
        return foi;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getInstitution() {
        return institution;
    }

    public String getGender() {
        return gender;
    }

    public String getUid() {
        return uid;
    }

    public double getLat() {
        return lat;
    }

    public double getLan() {
        return lan;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setFoi(String foi) {
        this.foi = foi;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
