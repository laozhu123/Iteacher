package com.li.zjut.iteacher.widget.common;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.li.zjut.iteacher.bean.imteacher.ContentData;
import com.li.zjut.iteacher.widget.WordWrapView;

import java.util.List;

/**
 * Created by LaoZhu on 2016/7/25.
 */
public class PeopleListManager {
    private Context context;
    private WordWrapView wwView;
    private View.OnClickListener mListener;
    private View.OnLongClickListener mLongListener;

    public PeopleListManager(Context context) {
        this.context = context;
    }

    public void setContentView(WordWrapView wwView) {
        this.wwView = wwView;
    }

    public void setmListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    public void setmLongListener(View.OnLongClickListener mLongListener) {
        this.mLongListener = mLongListener;
    }

    public void addView(View v) {
        v.setOnClickListener(mListener);
        v.setOnLongClickListener(mLongListener);
        wwView.addView(v);
    }


    public void clear() {
        wwView.removeAllViews();
    }
}
