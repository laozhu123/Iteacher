package com.li.zjut.iteacher.app;

import com.squareup.otto.Bus;

/**
 * Created by LaoZhu on 2016/9/12.
 */
public class AppBus  extends Bus {
    private static AppBus bus;

    public static AppBus getInstance() {
        if (bus == null) {
            bus = new AppBus();
        }
        return bus;
    }
}
