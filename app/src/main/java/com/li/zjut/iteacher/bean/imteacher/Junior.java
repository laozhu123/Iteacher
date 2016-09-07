package com.li.zjut.iteacher.bean.imteacher;

/**
 * Created by LaoZhu on 2016/6/27.
 */
public class Junior {

    String id;
    String name;
    String mobile;
    String picture;

    public Junior(String id, String name, String mobile, String picture) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
