package com.li.zjut.iteacher.app;

import android.app.Application;

import cn.smssdk.SMSSDK;

/**
 * Created by LaoZhu on 2016/5/12.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SMSSDK.initSDK(this, "132742f5b37e8", "799757b88bea7bf8d6d2843d45d9cfc8");
    }
}
