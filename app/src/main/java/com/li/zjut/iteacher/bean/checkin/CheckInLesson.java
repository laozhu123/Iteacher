package com.li.zjut.iteacher.bean.checkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/7/1.
 */
public class CheckInLesson implements Serializable{

    String lessonName;
    List<CheckInClass> list;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public List<CheckInClass> getList() {
        return list;
    }

    public void setList(List<CheckInClass> list) {
        this.list = list;
    }
}

