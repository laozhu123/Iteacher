package com.li.zjut.iteacher.activity.checkin.bean;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/8/3.
 */
public class Place implements Serializable {
    String name;
    String detail;
    double latitude;
    double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
