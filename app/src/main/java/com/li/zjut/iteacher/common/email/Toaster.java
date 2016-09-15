package com.li.zjut.iteacher.common.email;

import android.widget.Toast;

import com.li.zjut.iteacher.app.MyApplication;

/**
 * @author michael
 */
public class Toaster
{

    public static void show(String tip, boolean isLongTime)
    {

        Toast.makeText(MyApplication.context, tip, isLongTime == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static void show(String tip)
    {
        Toaster.show(tip, false);
    }
}
