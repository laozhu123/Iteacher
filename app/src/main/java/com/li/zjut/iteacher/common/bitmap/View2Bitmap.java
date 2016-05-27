package com.li.zjut.iteacher.common.bitmap;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by LaoZhu on 2016/5/27.
 */
public class View2Bitmap {
    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view,int x,int y) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.layout(0, 0, x, y);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
