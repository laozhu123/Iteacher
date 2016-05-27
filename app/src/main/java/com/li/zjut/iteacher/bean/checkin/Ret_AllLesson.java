package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/5/26.
 */
public class Ret_AllLesson implements Serializable{

    List<Lesson> list;

    public List<Lesson> getList() {
        return list;
    }

    public void setList(List<Lesson> list) {
        this.list = list;
    }
}
