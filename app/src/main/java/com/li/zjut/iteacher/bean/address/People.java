package com.li.zjut.iteacher.bean.address;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class People implements Serializable{

    private String name;
    private String phone;
    private String userid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
