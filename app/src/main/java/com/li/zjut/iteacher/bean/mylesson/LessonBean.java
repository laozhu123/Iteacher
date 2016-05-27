package com.li.zjut.iteacher.bean.mylesson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/5/17.
 */
public class LessonBean implements Serializable{

    String coursename;
    List<Curriculum> times;

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public List<Curriculum> getTimes() {
        return times;
    }

    public void setTimes(List<Curriculum> times) {
        this.times = times;
    }
}
