package com.li.zjut.iteacher.adapter.imteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.imteacher.Imteacher_task;
import com.li.zjut.iteacher.bean.imteacher.MyStu;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class ImteacherTaskListAdapter extends BaseAdapter {

    private List<Imteacher_task> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public ImteacherTaskListAdapter(List<Imteacher_task> list, Context context) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
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
            convertView = mInflater.inflate(R.layout.item_imteacher_task,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.taskName = (TextView) convertView.findViewById(R.id.tv_taskName);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.description = (TextView) convertView.findViewById(R.id.tv_description);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.taskName.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime());
        holder.description.setText(list.get(position).getDescription());
        return convertView;
    }

    final class ViewHolder {
        public TextView taskName,time,description;
    }

    public interface OnItemClick {
        void onclick(int id);
    }
}
