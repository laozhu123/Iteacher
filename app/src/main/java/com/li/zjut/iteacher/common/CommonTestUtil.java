package com.li.zjut.iteacher.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LaoZhu on 2016/5/23.
 */
public class CommonTestUtil {

    public static void toast(Context c,String s){
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }
}
