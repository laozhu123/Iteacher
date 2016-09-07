package com.li.zjut.iteacher.activity.checkin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkin.bean.CheckBean;
import com.li.zjut.iteacher.activity.checkin.bean.StuCheckBean;
import com.li.zjut.iteacher.common.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.utils.CommonUtils;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class StuCheckInfoListAdapter extends BaseAdapter {

    private List<StuCheckBean> list;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public StuCheckInfoListAdapter(List<StuCheckBean> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_stu_check_info,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPlace = (TextView) convertView.findViewById(R.id.tv_place);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.imgHead = (CircleImageView) convertView.findViewById(R.id.img_head);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.tvName.setText(list.get(position).getName());
        holder.tvPlace.setText(list.get(position).getPlace());
        holder.tvTime.setText("签到 " + list.get(position).getTime());
        if (this.list.get(position).getName().length() >= 2)
            holder.imgHead.setImageDrawable(Utils.getCircleBgSize(this.list.get(position).getName().substring(this.list.get(position).getName().length() - 2), context));
        else
            holder.imgHead.setImageDrawable(Utils.getCircleBgSize(this.list.get(position).getName(), context));
        holder.tvPlace.setTag(position);
        holder.tvPlace.setOnClickListener(listener);
        return convertView;
    }

    final class ViewHolder {
        public TextView tvName, tvPlace, tvTime;
        public CircleImageView imgHead;
    }


}
