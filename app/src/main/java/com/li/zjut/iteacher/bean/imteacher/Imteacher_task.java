package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;

/**
 * Created by LaoZhu on 2016/6/23.
 */
public class Imteacher_task implements Serializable {
    String time, title, description;

    public Imteacher_task(String time, String title, String description) {
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
