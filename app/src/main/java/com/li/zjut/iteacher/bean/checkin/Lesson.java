package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/5/26.
 */
public class Lesson implements Serializable{

    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
