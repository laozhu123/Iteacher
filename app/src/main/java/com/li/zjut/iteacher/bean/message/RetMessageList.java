package com.li.zjut.iteacher.bean.message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/22.
 */
public class RetMessageList implements Serializable{

    List<MessageContent> list;

    public List<MessageContent> getList() {
        return list;
    }

    public void setList(List<MessageContent> list) {
        this.list = list;
    }
}
