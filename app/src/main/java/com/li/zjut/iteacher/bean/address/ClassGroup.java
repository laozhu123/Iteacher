package com.li.zjut.iteacher.bean.address;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/7/11.
 */
public class ClassGroup implements Serializable {
    private String picUrl, classGroupName;
    private int classGroupTeammateNum;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public int getClassGroupTeammateNum() {
        return classGroupTeammateNum;
    }

    public void setClassGroupTeammateNum(int classGroupTeammateNum) {
        this.classGroupTeammateNum = classGroupTeammateNum;
    }
}
