package com.li.zjut.iteacher.adapter.mylesson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.li.zjut.iteacher.R;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class WeekSelectListAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater mInflater;
    private Context context;
    private int index = 0;

    public WeekSelectListAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_week_select,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.text = (TextView) convertView.findViewById(R.id.helo);
            holder.text.setText(list.get(position));
            holder.text.setTag(position);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            holder.text.setText(list.get(position));
        }
        if (index != 0) {
            if (index == position + 1){
                holder.text.setSelected(true);
            }else{
                holder.text.setSelected(false);
            }
        } else {
            if (list.get(position).length() > 5)
                holder.text.setSelected(true);
            else
                holder.text.setSelected(false);
        }

        return convertView;
    }

    final class ViewHolder {
        public TextView text;
    }

    public void setSelectWeek(int index) {
        this.index = index;
    }

}
