package com.li.zjut.iteacher.bean.message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/22.
 */
public class RetCommentList implements Serializable{
    List<CommentContent> list;

    public List<CommentContent> getList() {
        return list;
    }

    public void setList(List<CommentContent> list) {
        this.list = list;
    }
}
