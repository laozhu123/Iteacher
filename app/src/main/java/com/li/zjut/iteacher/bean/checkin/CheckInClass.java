package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/7/1.
 */
public class CheckInClass implements Serializable{

    String id;
    String className;
    String classDetail;

    public String getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(String classDetail) {
        this.classDetail = classDetail;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
