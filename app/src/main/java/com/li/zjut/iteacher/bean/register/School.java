package com.li.zjut.iteacher.bean.register;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/5/23.
 */
public class School implements Serializable{

    String schoolname,introduction;
    int totalclassnum,id;


    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public int getTotalclassnum() {
        return totalclassnum;
    }

    public void setTotalclassnum(int totalclassnum) {
        this.totalclassnum = totalclassnum;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
