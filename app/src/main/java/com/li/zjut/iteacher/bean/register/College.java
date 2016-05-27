package com.li.zjut.iteacher.bean.register;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/5/23.
 */
public class College implements Serializable{

    String collegename;
    int campusid,id,parent_collegeid,schoolid;

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public int getCampusid() {
        return campusid;
    }

    public void setCampusid(int campusid) {
        this.campusid = campusid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_collegeid() {
        return parent_collegeid;
    }

    public void setParent_collegeid(int parent_collegeid) {
        this.parent_collegeid = parent_collegeid;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }
}
