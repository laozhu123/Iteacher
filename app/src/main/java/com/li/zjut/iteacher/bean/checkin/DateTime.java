package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/17.
 */
public class DateTime implements Serializable{

    String date;
    List<CheckPoint> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CheckPoint> getList() {
        return list;
    }

    public void setList(List<CheckPoint> list) {
        this.list = list;
    }
}
