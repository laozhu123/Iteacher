package com.li.zjut.iteacher.adapter.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.address.ClassGroup;
import com.li.zjut.iteacher.bean.imteacher.Imteacher_task;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class ClassGroupListtAdapter extends BaseAdapter {

    private List<ClassGroup> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public ClassGroupListtAdapter(List<ClassGroup> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_class_group,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.tvGroupName = (TextView) convertView.findViewById(R.id.tv_group_name);
            holder.teammateNum = (TextView) convertView.findViewById(R.id.teammate_num);
            holder.imgPic = (ImageView) convertView.findViewById(R.id.img_pic);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.tvGroupName.setText(list.get(position).getClassGroupName());
        holder.teammateNum.setText(list.get(position).getClassGroupTeammateNum()+"人");
        return convertView;
    }

    final class ViewHolder {
        public TextView tvGroupName,teammateNum;
        public ImageView imgPic;
    }

    public interface OnItemClick {
        void onclick(int id);
    }
}
