package com.li.zjut.iteacher.adapter.schedule;

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
public class RepeatAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public RepeatAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
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
            convertView = mInflater.inflate(R.layout.item,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.text = (TextView) convertView.findViewById(R.id.helo);
            holder.text.setText(list.get(position));
            holder.text.setTag(position);
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onclick(Integer.parseInt(v.getTag().toString()));
                }
            });
            convertView.setTag(holder);//绑定ViewHolder对象
        } else{
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            holder.text.setText(list.get(position));
        }
        return convertView;
    }

    final class ViewHolder{
        public TextView text;
    }

    public interface OnItemClick{
        void onclick(int id);
    }
}
