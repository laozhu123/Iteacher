package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/23.
 */
public class Ret_my_stu implements Serializable{

    List<MyStu> list;

    public List<MyStu> getList() {
        return list;
    }

    public void setList(List<MyStu> list) {
        this.list = list;
    }
}
