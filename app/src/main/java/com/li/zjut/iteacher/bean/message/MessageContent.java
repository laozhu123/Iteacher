package com.li.zjut.iteacher.bean.message;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/22.
 */
public class MessageContent implements Serializable {
    String description;
    String imgUri;
    String title;
    int type;
    String time;

    public MessageContent(String description, String imgUri, String title, int type, String time) {
        this.description = description;
        this.imgUri = imgUri;
        this.title = title;
        this.type = type;
        this.time = time;
    }


    public MessageContent(String description, String imgUri, String title) {
        this.description = description;
        this.imgUri = imgUri;
        this.title = title;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
