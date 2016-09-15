package com.li.zjut.iteacher.app;

import com.li.zjut.iteacher.common.observer.ObserverImpl;
import com.li.zjut.iteacher.common.observer.SubjectImpl;
import com.li.zjut.iteacher.common.observer.SubjectImpl_sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoZhu on 2016/9/12.
 */
public class Subject_sms implements SubjectImpl, SubjectImpl_sms {

    List<ObserverImpl> arr = new ArrayList<>();

    @Override
    public void attach(ObserverImpl a) {
        if (!arr.contains(a))
            arr.add(a);
    }

    @Override
    public void detach(ObserverImpl a) {
        if (arr.contains(a))
            arr.remove(a);
    }

    @Override
    public void notifyAll(int a) {
        for (ObserverImpl o : arr) {
            o.update(a);
        }
    }
}
