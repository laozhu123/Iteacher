package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class ThreeType implements Serializable{

    String type;
   List<Student> list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }
}
