package com.li.zjut.iteacher.common;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.DargChildInfo;
import com.li.zjut.iteacher.bean.DragIconInfo;

import java.util.ArrayList;

/**
 * Created by LaoZhu on 2016/5/11.
 */
public class StaticData {

    public final static String URL = "http://115.28.24.114:8080";
    public final static String WEATHER = "http://apicloud.mob.com";
    public final static String KEYWEATHER = "139388b8e8bd0";

    /*
    * SharedPreferences params
    * */
    public final static String USER_DATA = "USERDATA";
    public final static int SHARE_MODE = 0;

    public static ArrayList<DragIconInfo> iconInfoList = initPareList();
    public static ArrayList<DargChildInfo> childList = initChildList();


    /*
    * init all dragGrid
    * */
    private static ArrayList<DragIconInfo> initPareList() {

        ArrayList<DragIconInfo> iconInfoList = new ArrayList<>();
        iconInfoList.add(new DragIconInfo(1, "上课点到", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(2, "我的课表", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(3, "点到情况", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(4, "我的日程", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(5, "我是导师", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(6, "我的邮箱", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(7, "我是班主任", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(8, "我的业绩", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        iconInfoList.add(new DragIconInfo(9, "学生成绩", R.mipmap.ic_launcher, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
        return iconInfoList;
    }

    /**
     * 方法: initChildList <p>
     * 描述: 初始化child list <p>
     * 参数: @return <p>
     * 返回: ArrayList<DargChildInfo> <p>
     * 异常  <p>
     * 作者: wedcel wedcel@gmail.com <p>
     * 时间: 2015年8月25日 下午5:36:12
     */
    private static ArrayList<DargChildInfo> initChildList() {

        ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();
        childList.add(new DargChildInfo(1, "Item1"));
        childList.add(new DargChildInfo(2, "Item2"));
        childList.add(new DargChildInfo(3, "Item3"));
        childList.add(new DargChildInfo(4, "Item4"));
        childList.add(new DargChildInfo(5, "Item5"));
        childList.add(new DargChildInfo(6, "Item6"));
        childList.add(new DargChildInfo(7, "Item7"));
        return childList;
    }

    public static String country = "+86";
    public static boolean closeThread = true;

    public static String id = "";

    public static String cid = "";
}
