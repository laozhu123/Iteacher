package com.li.zjut.iteacher.widget.common;

/**
 * Created by LaoZhu on 2016/6/27.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.imteacher.ContentData;


public class Zhu_ContentViewManager {
    Context context;
    LinearLayout content;
    ArrayList<ContentData> contentData = new ArrayList<ContentData>();
    HashMap<String, Object> map = new HashMap<String, Object>();
    private OnClickListener listener;
    private OnLongClickListener longListener;

    public Zhu_ContentViewManager(Context context) {
        this.context = context;
    }

    public Zhu_ContentViewManager(Context context, LinearLayout layout) {
        content = layout;
        this.context = context;
    }

    public void setContentLinearLayout(LinearLayout layout) {
        content = layout;
    }

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setLongListener(OnLongClickListener longListener) {
        this.longListener = longListener;
    }

    @SuppressLint("NewApi")
    public void add(ContentData data) {
        contentData.add(data);
        TextView textview = new TextView(context);
        textview.setTag(data.getId());
        CharSequence text = data.getName();
        LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 5;
        params.rightMargin = 5;
        params.gravity = Gravity.CENTER_VERTICAL;
        textview.setPadding(10, 3, 10, 3);
        textview.setTextAppearance(context, R.style.addteamate_text_style);
        textview.setText(text);
        textview.setBackground(context.getResources().getDrawable(R.drawable.addteamate_text_bg));
        if (listener != null)
            textview.setOnClickListener(listener);
        if (longListener != null) {
            textview.setOnLongClickListener(longListener);
        }
        content.addView(textview, params);
        map.put(data.getId(), textview);
    }

    public void remove(String id) {
        content.removeView((View) map.get(id));
        for (int i = 0; i < contentData.size(); i++) {
            if (contentData.get(i).getId().equals(id)) {
                contentData.remove(i);
            }
        }
    }

    public ArrayList<ContentData> getContentData() {
        return contentData;
    }

    public void setContentData(ArrayList<ContentData> contentData) {
        this.contentData = contentData;
    }

    public int getViewCount() {
        return contentData.size();
    }

    public void clear() {
        contentData.clear();
        content.removeAllViews();
    }
}