package com.li.zjut.iteacher.bean.register;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/5/24.
 */
public class Campus implements Serializable{

    int id,schoolid;
    String coursebegintime,campusname,weekbegindate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public String getCoursebegintime() {
        return coursebegintime;
    }

    public void setCoursebegintime(String coursebegintime) {
        this.coursebegintime = coursebegintime;
    }

    public String getCampusname() {
        return campusname;
    }

    public void setCampusname(String campusname) {
        this.campusname = campusname;
    }

    public String getWeekbegindate() {
        return weekbegindate;
    }

    public void setWeekbegindate(String weekbegindate) {
        this.weekbegindate = weekbegindate;
    }
}
