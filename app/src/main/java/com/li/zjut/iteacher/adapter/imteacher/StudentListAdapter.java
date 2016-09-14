package com.li.zjut.iteacher.adapter.imteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.imteacher.Imteacher_task;
import com.li.zjut.iteacher.bean.imteacher.Student;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class StudentListAdapter extends BaseAdapter {

    private List<Student> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;

    public StudentListAdapter(List<Student> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_student,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.pic = (CircleImageView) convertView.findViewById(R.id.pic);
            holder.content = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.name.setText(list.get(position).getName());
        if (position % 2 == 1){
            holder.pic.setImageResource(R.mipmap.head1);
            holder.content.setText("研1");
        }
        else{
            holder.pic.setImageResource(R.mipmap.head2);
            holder.content.setText("研2");
        }

        return convertView;
    }

    final class ViewHolder {
        public TextView name;
        public TextView content;
        public CircleImageView pic;
    }

    public interface OnItemClick {
        void onclick(int id);
    }
}
