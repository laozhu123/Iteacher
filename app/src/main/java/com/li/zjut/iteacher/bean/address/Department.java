package com.li.zjut.iteacher.bean.address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class Department implements Serializable{

    private String name;
    private List<People> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<People> getList() {
        return list;
    }

    public void setList(List<People> list) {
        this.list = list;
    }
}
