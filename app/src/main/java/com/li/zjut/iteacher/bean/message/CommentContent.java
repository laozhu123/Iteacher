package com.li.zjut.iteacher.bean.message;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/22.
 */
public class CommentContent implements Serializable {
    String name;
    String content;
    String time;
    int agree;
    int deny;

    public CommentContent(String name, String content,String time, int agree, int deny) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.agree = agree;
        this.deny = deny;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getDeny() {
        return deny;
    }

    public void setDeny(int deny) {
        this.deny = deny;
    }
}
