package com.li.zjut.iteacher.adapter.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.message.CommentContent;
import com.li.zjut.iteacher.bean.message.MessageContent;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class CommentListAdapter extends BaseAdapter {

    private List<CommentContent> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public CommentListAdapter(List<CommentContent> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_comment1,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.agree = (ImageView) convertView.findViewById(R.id.agree);
            holder.disagree = (ImageView) convertView.findViewById(R.id.disagree);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.name.setText(list.get(position).getName());
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getTime());
        return convertView;
    }

    final class ViewHolder {
        public TextView name;
        public TextView content,time;
        public ImageView agree,disagree;
    }

    public interface OnItemClick {
        void onclick(int id);
    }
}
