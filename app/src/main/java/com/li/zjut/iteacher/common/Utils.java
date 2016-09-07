package com.li.zjut.iteacher.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.li.zjut.iteacher.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by LaoZhu on 2016/5/12.
 */
public class Utils {
    private static Random random = new Random();

    /**
     * <br>功能简述:画出小红点
     * <br>功能详细描述:
     * <br>注意:
     *
     * @param count
     */
    public static Drawable initCounterResources(int count, Context mContext) {
        Drawable mCounterDrawable = null;
        Bitmap bitmapDrawable = null;

        if (mCounterDrawable == null) {
            // 初始化画布
            mCounterDrawable = mContext.getResources().getDrawable(R.drawable.alm_lesson_icon_unread);
            Bitmap bitmapDrawables = ((BitmapDrawable) mCounterDrawable).getBitmap();
            int bitmapX = bitmapDrawables.getWidth();
            int bitmapY = bitmapDrawables.getHeight();
            bitmapDrawable = Bitmap.createBitmap(bitmapX, bitmapY, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapDrawable);

            // 拷贝图片
            Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setDither(true);// 防抖动
            mPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯
            Rect src = new Rect(0, 0, bitmapX, bitmapX);
            Rect dst = new Rect(0, 0, bitmapX, bitmapX);
            canvas.drawBitmap(((BitmapDrawable) mCounterDrawable).getBitmap(), src, dst, mPaint);

            //            canvas.drawBitmap(bitmapDrawable, bitmapX, bitmapY, mPaint);
            //画数字
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(20);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

            canvas.drawText(String.valueOf(count), 20, 26, mPaint);
            canvas.save();
        }
        return new BitmapDrawable(mContext.getResources(), bitmapDrawable);
    }


    /**
     * <br>功能详细描述:
     * <br>注意:
     */
    public static Drawable getCircleBg(String name, Context mContext) {
        Bitmap bitmapDrawable = null;
        int x = DensityUtil.dip2px(mContext, 30);
        int y = DensityUtil.dip2px(mContext, 30);
        // 初始化画布
        bitmapDrawable = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapDrawable);

        // 拷贝图片
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);// 防抖动
        mPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯

        int ran = random.nextInt(100);
        mPaint.setColor(mContext.getResources().getColor(StaticData.circle_bg[ran % StaticData.circle_bg.length]));

        //定义一个矩形区域
        RectF oval = new RectF(0, 0, x, y);
        //矩形区域内切椭圆
        canvas.drawOval(oval, mPaint);

        //            canvas.drawBitmap(bitmapDrawable, bitmapX, bitmapY, mPaint);
        //画数字
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(23);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        canvas.drawText(name, 30, 39, mPaint);
        canvas.save();

        return new BitmapDrawable(mContext.getResources(), bitmapDrawable);
    }

    /**
     * <br>功能详细描述:
     * <br>注意:
     */
    public static Drawable getCircleBgSize(String name, Context mContext) {
        Bitmap bitmapDrawable = null;
        int x = DensityUtil.dip2px(mContext, 45);
        int y = DensityUtil.dip2px(mContext, 45);
        // 初始化画布
        bitmapDrawable = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapDrawable);

        // 拷贝图片
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);// 防抖动
        mPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯

        int ran = random.nextInt(100);
        mPaint.setColor(mContext.getResources().getColor(StaticData.circle_bg[ran % StaticData.circle_bg.length]));

        //定义一个矩形区域
        RectF oval = new RectF(0, 0, x, y);
        //矩形区域内切椭圆
        canvas.drawOval(oval, mPaint);

        //            canvas.drawBitmap(bitmapDrawable, bitmapX, bitmapY, mPaint);
        //画数字
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        canvas.drawText(name, 45, 59, mPaint);
        canvas.save();

        return new BitmapDrawable(mContext.getResources(), bitmapDrawable);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context c) {
        try {
            PackageManager manager = c.getPackageManager();
            PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /*
    * 产生随机字符串
    * */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //listview重新设置高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 裁剪URL
     *
     * @param s
     * @return
     */
    public static String clipURL(String s) {
        if (!TextUtils.isEmpty(s)) {
            String[] strs = s.split("/");
            StringBuilder sb = new StringBuilder(ValuesH.URL);
            for (int i = 0; i < strs.length; i++) {
                if (i > 5 && i < strs.length - 1) {
                    sb.append(strs[i] + "/");
                }
                if (i == strs.length - 1) {
                    sb.append(strs[i]);
                }
            }
            Log.e("URLClip", sb.toString());
            return sb.toString();
        } else {
            return "";
        }
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
