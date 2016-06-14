package com.li.zjut.iteacher.common.weather;

import com.li.zjut.iteacher.R;

/**
 * Created by LaoZhu on 2016/6/7.
 */
public class WeatherImg {

    /**
     *
     * 方法名：imageResoId
     * 功能：获取图片
     * 参数：
     * @param weather
     * @return
     * 创建人：huanghsh
     * 创建时间：2012-10-17
     */
    public static int imageResoId(String weather){
        int resoid=R.drawable.s_2;
        if(weather.indexOf("多云")!=-1||weather.indexOf("晴")!=-1){//多云转晴，以下类同 indexOf:包含字串
            resoid=R.drawable.s_1;}
        else if(weather.indexOf("多云")!=-1&&weather.indexOf("阴")!=-1){
            resoid=R.drawable.s_2;}
        else if(weather.indexOf("阴")!=-1&&weather.indexOf("雨")!=-1){
            resoid=R.drawable.s_3;}
        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雨")!=-1){
            resoid= R.drawable.s_12;}
        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雾")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("晴")!=-1){resoid=R.drawable.s_13;}
        else if(weather.indexOf("多云")!=-1){resoid=R.drawable.s_2;}
        else if(weather.indexOf("阵雨")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("小雨")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("中雨")!=-1){resoid=R.drawable.s_4;}
        else if(weather.indexOf("大雨")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("暴雨")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("冰雹")!=-1){resoid=R.drawable.s_6;}
        else if(weather.indexOf("雷阵雨")!=-1){resoid=R.drawable.s_7;}
        else if(weather.indexOf("小雪")!=-1){resoid=R.drawable.s_8;}
        else if(weather.indexOf("中雪")!=-1){resoid=R.drawable.s_9;}
        else if(weather.indexOf("大雪")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("暴雪")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("扬沙")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("沙尘")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("雾")!=-1){resoid=R.drawable.s_12;}
        return resoid;
    }
}
