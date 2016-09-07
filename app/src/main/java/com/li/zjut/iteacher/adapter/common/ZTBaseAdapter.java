package com.li.zjut.iteacher.adapter.common;

/**
 * Created by LaoZhu on 2016/6/27.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ZTBaseAdapter extends BaseAdapter{

    protected ArrayList<HashMap<String, Object>> data;
    protected Context context;
    public ZTBaseAdapter(Context context, ArrayList<HashMap<String, Object>> data) {

        // TODO Auto-generated constructor stub
        this.data = data;
        this.context = context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void setData(ArrayList<HashMap<String, Object>> data){
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void addData(HashMap<String, Object> map){
        data.add(0, map);
        this.notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}