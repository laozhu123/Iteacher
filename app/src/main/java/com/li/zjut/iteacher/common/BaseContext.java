package com.li.zjut.iteacher.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LaoZhu on 2016/7/1.
 */
public interface BaseContext {
    public void addView(ViewGroup parent, View content);
    public void startLoading();
    public void stopLoading();
}
