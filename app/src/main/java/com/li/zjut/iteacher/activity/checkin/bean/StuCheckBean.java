package com.li.zjut.iteacher.activity.checkin.bean;

import com.amap.api.maps.model.LatLng;

/**
 * Created by LaoZhu on 2016/8/4.
 */
public class StuCheckBean {
    private String name, place;
    private LatLng latLng;
    private String time;
    private int id;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
