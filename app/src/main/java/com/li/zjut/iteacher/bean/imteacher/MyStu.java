package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/23.
 */
public class MyStu implements Serializable{

    String name;
    String id;
    int num;

    public MyStu(String name, String id, int num) {
        this.name = name;
        this.id = id;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
