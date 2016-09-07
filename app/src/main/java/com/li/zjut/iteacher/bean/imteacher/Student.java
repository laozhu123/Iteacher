package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/27.
 */
public class Student implements Serializable {
    String userid, name, mobile, pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
}
