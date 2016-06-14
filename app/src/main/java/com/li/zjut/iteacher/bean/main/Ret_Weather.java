package com.li.zjut.iteacher.bean.main;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class Ret_Weather {

    String msg,retCode;
    List<Condition> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<Condition> getResult() {
        return result;
    }

    public void setResult(List<Condition> result) {
        this.result = result;
    }
}
