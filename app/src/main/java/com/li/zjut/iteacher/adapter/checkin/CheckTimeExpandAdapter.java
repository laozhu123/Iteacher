package com.li.zjut.iteacher.adapter.checkin;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.checkin.Curriculum;
import com.li.zjut.iteacher.bean.checkin.DateTime;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/2.
 */
public class CheckTimeExpandAdapter implements ExpandableListAdapter {

    private List<DateTime> mDatas;
    private Context context;

    public CheckTimeExpandAdapter(Context context, List<DateTime> mDatas) {

        this.context = context;
        this.mDatas = mDatas;
    }

    public void setmDatas(List<DateTime> mDatas) {

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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_item_checkinfo, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.parent_textview);
        tv.setText(mDatas.get(groupPosition).getDate());
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String info = mDatas.get(groupPosition).getList().get(childPosition).getTime();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item_checkinfo, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.second_textview);
        tv.setText(info);
        return tv;
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
}
