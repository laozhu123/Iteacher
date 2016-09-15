package com.li.zjut.iteacher.common.observer;

/**
 * Created by LaoZhu on 2016/9/12.
 */
public interface SubjectImpl {
    void attach(ObserverImpl a);

    void detach(ObserverImpl a);
}
