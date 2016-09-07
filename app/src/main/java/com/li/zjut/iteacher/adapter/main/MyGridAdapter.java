package com.li.zjut.iteacher.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.adapter.BaseViewHolder;
import com.li.zjut.iteacher.common.Utils;

public class MyGridAdapter extends BaseAdapter {
    private Context mContext;

    private String[] img_text = {"上课点到", "我的课表", "我的日程", "我是导师", "我的邮箱", "我是班主任", "学生成绩"};
    private int[] imgs = {R.mipmap.home_1, R.mipmap.home_2, R.mipmap.home_4,
            R.mipmap.home_5, R.mipmap.home_6, R.mipmap.home_7, R.mipmap.home_9};
    int todayLessonNum = 0, todaySchedule = 0;


    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void setTodayLessonNum(int todayLessonNum) {
        this.todayLessonNum = todayLessonNum;
    }

    public void setTodaySchedule(int todaySchedule) {
        this.todaySchedule = todaySchedule;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_grid_9, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        ImageView countDot = BaseViewHolder.get(convertView, R.id.count_dot);
        switch (position) {
            case 1:
                if (todayLessonNum > 0) {
                    countDot.setVisibility(View.VISIBLE);
                    countDot.setImageDrawable(Utils.initCounterResources(todayLessonNum, mContext));
                }
                break;
            case 2:
                if (todaySchedule > 0) {
                    countDot.setVisibility(View.VISIBLE);
                    countDot.setImageDrawable(Utils.initCounterResources(todaySchedule, mContext));
                }
                break;
        }
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }


}