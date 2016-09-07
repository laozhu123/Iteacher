package com.li.zjut.iteacher.adapter.checkin;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkin.adapter.PlaceListAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;
import com.li.zjut.iteacher.bean.checkin.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class CheckAllClassesExpandAdapter implements ExpandableListAdapter {

    private List<CheckInLesson> mDatas;
    private Context context;
    private OnSelectListener onSelectListener;
    private int expandIndex = 1;
    private boolean firsttime = true;
    private ImageView expandImg = null;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public CheckAllClassesExpandAdapter(Context context, List<CheckInLesson> mDatas) {

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
            convertView = inflater.inflate(R.layout.item_group_cur_class, null);
        }
        if (groupPosition == 0) {
            convertView.findViewById(R.id.tv_cur_name).setVisibility(View.GONE);
            convertView.findViewById(R.id.img_arrow).setVisibility(View.GONE);
            convertView.findViewById(R.id.line).setVisibility(View.GONE);
            return convertView;
        }
        convertView.findViewById(R.id.tv_cur_name).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.img_arrow).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.line).setVisibility(View.VISIBLE);
        TextView tvCurName = (TextView) convertView
                .findViewById(R.id.tv_cur_name);
        Log.d("helo", mDatas.get(groupPosition).getLessonName() + getGroupCount());
        tvCurName.setText(mDatas.get(groupPosition).getLessonName());
        convertView.findViewById(R.id.img_arrow).setTag(groupPosition);
        convertView.setTag(groupPosition);
        convertView.setOnClickListener(onClickListener);

        if (groupPosition == 1 && firsttime) {
            ((ImageView) convertView.findViewById(R.id.img_arrow)).setImageResource(R.mipmap.item_arrow_selected);
            firsttime = false;
            expandImg = ((ImageView) convertView.findViewById(R.id.img_arrow));
        }
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_child_cur_class, null);
        }
        TextView tvClassName = (TextView) convertView
                .findViewById(R.id.tv_class_name);
        TextView tvClassInfo = (TextView) convertView.findViewById(R.id.tv_class_info);
        tvClassName.setText(mDatas.get(groupPosition).getList().get(childPosition).getClassName());
        tvClassInfo.setText(mDatas.get(groupPosition).getList().get(childPosition).getClassDetail());
        return convertView;
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
