package com.li.zjut.iteacher.bean.register;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/5/24.
 */
public class Ret_Allcollege implements Serializable {

    String des;
    int rt;
    List<College> list;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public List<College> getList() {
        return list;
    }

    public void setList(List<College> list) {
        this.list = list;
    }
}
