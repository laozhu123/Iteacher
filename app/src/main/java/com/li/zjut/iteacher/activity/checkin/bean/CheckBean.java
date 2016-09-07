package com.li.zjut.iteacher.activity.checkin.bean;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/8/4.
 */
public class CheckBean implements Serializable{
    private String type;
    private int peopleTypeNum;
    private int peopleAllNum;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeopleTypeNum() {
        return peopleTypeNum;
    }

    public void setPeopleTypeNum(int peopleTypeNum) {
        this.peopleTypeNum = peopleTypeNum;
    }

    public int getPeopleAllNum() {
        return peopleAllNum;
    }

    public void setPeopleAllNum(int peopleAllNum) {
        this.peopleAllNum = peopleAllNum;
    }
}
