package com.li.zjut.iteacher.bean.imteacher;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/23.
 */
public class Ret_imteacher_task implements Serializable{
    List<Imteacher_task> list;

    public List<Imteacher_task> getList() {
        return list;
    }

    public void setList(List<Imteacher_task> list) {
        this.list = list;
    }
}
