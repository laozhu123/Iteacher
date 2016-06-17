package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/17.
 */
public class Curriculum implements Serializable{

    String name;
    List<CheckPoint> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckPoint> getList() {
        return list;
    }

    public void setList(List<CheckPoint> list) {
        this.list = list;
    }
}
