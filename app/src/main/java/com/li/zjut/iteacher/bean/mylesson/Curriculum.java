package com.li.zjut.iteacher.bean.mylesson;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/5/5.
 */
public class Curriculum implements Serializable {

    private int begin, end, weekday, index, fromWeek, endWeek, single_double,id;
    private String text, place,courseTimeId;

    public Curriculum(int weekday, int begin, int end, String text, int index, String palce, int fromWeek, int endWeek, int single_double,String courseTimeId,int id) {
        this.begin = begin;
        this.end = end;
        this.weekday = weekday;
        this.text = text;
        this.index = index;//index是该项在数组中的位置
        this.place = palce;
        this.fromWeek = fromWeek;
        this.endWeek = endWeek;
        this.single_double = single_double;
        this.courseTimeId = courseTimeId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(String courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public Curriculum() {
    }

    public int getFromWeek() {
        return fromWeek;
    }

    public void setFromWeek(int fromWeek) {
        this.fromWeek = fromWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getSingle_double() {
        return single_double;
    }

    public void setSingle_double(int single_double) {
        this.single_double = single_double;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }
}
