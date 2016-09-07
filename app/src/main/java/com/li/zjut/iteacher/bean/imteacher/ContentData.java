package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/27.
 */
public class ContentData implements Serializable{
    String id;
    String name;
    String mobile;


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public ContentData(String id, String name,String mobile){
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public ContentData(String id, String name){
        this.id = id;
        this.name = name;
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
}
