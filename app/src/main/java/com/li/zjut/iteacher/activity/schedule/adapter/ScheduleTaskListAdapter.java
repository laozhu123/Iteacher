package com.li.zjut.iteacher.activity.schedule.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;
import com.li.zjut.iteacher.widget.WordWrapView;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class ScheduleTaskListAdapter implements ExpandableListAdapter {

    private List<CheckInLesson> mDatas;
    private Context context;
    private OnSelectListener onSelectListener;
    private int expandIndex = 1;
    private boolean firsttime = true;
    private ImageView expandImg = null;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public ScheduleTaskListAdapter(Context context, List<CheckInLesson> mDatas) {

        this.context = context;
        this.mDatas = mDatas;
    }

    public void setmDatas(List<CheckInLesson> mDatas) {

        this.mDatas = mDatas;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDatas.get(groupPosition).getList() != null)
            return mDatas.get(groupPosition).getList().size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("helo", v.getTag().toString());
            if ((int) v.getTag() == expandIndex) {
                Log.d("helo", "p1");
                ((ImageView) v.findViewById(R.id.img_arrow)).setImageResource(R.mipmap.item_arrow_normal);
                expandIndex = -1;
            } else {
                Log.d("helo", "p2");
                if (expandIndex != -1) {
                    Log.d("helo", "p3");
                    expandImg.setImageResource(R.mipmap.item_arrow_normal);
                }

                expandImg = (ImageView) v.findViewById(R.id.img_arrow);
                Log.d("helo", expandImg.getTag() + "");
                expandImg.setImageResource(R.mipmap.item_arrow_selected);
                expandIndex = (int) v.getTag();
            }
            onSelectListener.onSelect((int) v.getTag());
        }
    };


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_parent_schedule_task, null);
        }
        if (groupPosition == 0) {
            convertView.findViewById(R.id.img_pic).setVisibility(View.GONE);
            convertView.findViewById(R.id.tv_type).setVisibility(View.GONE);
            convertView.findViewById(R.id.img_arrow).setVisibility(View.GONE);
            convertView.findViewById(R.id.line).setVisibility(View.GONE);
            return convertView;
        }
        convertView.findViewById(R.id.img_pic).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.tv_type).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.img_arrow).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.line).setVisibility(View.VISIBLE);

        TextView tvType = (TextView) convertView
                .findViewById(R.id.tv_type);
        Log.d("helo", mDatas.get(groupPosition).getLessonName() + getGroupCount());
        tvType.setText(mDatas.get(groupPosition).getLessonName());
        convertView.findViewById(R.id.img_arrow).setTag(groupPosition);
        convertView.setTag(groupPosition);
        convertView.setOnClickListener(onClickListener);

        if (groupPosition == 1 && firsttime) {
            ((ImageView) convertView.findViewById(R.id.img_arrow)).setImageResource(R.mipmap.item_arrow_selected);
            firsttime = false;
            expandImg = ((ImageView) convertView.findViewById(R.id.img_arrow));
        }

        if (groupPosition == 1) {
            ((ImageView) convertView.findViewById(R.id.img_pic)).setImageResource(R.mipmap.item_schedule);
        }
        if (groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.img_pic)).setImageResource(R.mipmap.item_task);
        }
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_child_schedule_task, null);
        }
        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText("10:30");
        TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText("中午开会");
        WordWrapView llTags = (WordWrapView) convertView.findViewById(R.id.tags);
        llTags.removeAllViews();
        for (int i = 0; i < 3; i++) {
            addView("重要", llTags);
        }
        return convertView;
    }

    private void addView(String s, WordWrapView llTags) {
        TextView textview = new TextView(context);
        textview.setText(s);
        textview.setBackgroundResource(R.drawable.tag_bg);
        ColorStateList csl = (ColorStateList) context.getResources().getColorStateList(R.color.tag_text_color);
        textview.setTextColor(csl);
        textview.setSelected(true);
        llTags.addView(textview);
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public static interface OnSelectListener {
        public void onSelect(int index);
    }
}

