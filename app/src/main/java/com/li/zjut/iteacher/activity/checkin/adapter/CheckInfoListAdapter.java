package com.li.zjut.iteacher.activity.checkin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkin.bean.CheckBean;
import com.li.zjut.iteacher.activity.checkin.bean.Place;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.utils.CommonUtils;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class CheckInfoListAdapter extends BaseAdapter {

    private List<CheckBean> list;
    private LayoutInflater mInflater;
    private Context context;


    public CheckInfoListAdapter(List<CheckBean> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_check_info,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tvPeopleNum = (TextView) convertView.findViewById(R.id.tv_people_num);
            holder.lineYellow = convertView.findViewById(R.id.yellow_line);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.tvPeopleNum.setText(list.get(position).getPeopleTypeNum() + "人");
        holder.tvType.setText(list.get(position).getType());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.lineYellow.getLayoutParams();

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        holder.lineYellow.setVisibility(View.VISIBLE);

        change(holder.lineYellow, position);

        return convertView;
    }

    final class ViewHolder {
        public TextView tvType, tvPeopleNum;
        public View lineYellow;
    }

    public interface OnItemClick {
        void onclick(int id);
    }

    private void change(View v, int index) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        width -= CommonUtils.dip2px(context, 32);
        layoutParams.height = CommonUtils.dip2px(context, 6);

        Log.d("helo", index + "");
        float width1 = width * ((float) list.get(index).getPeopleTypeNum() / (float) list.get(index).getPeopleAllNum());
        layoutParams.width = (int) width1;
        v.setLayoutParams(layoutParams);
        v.requestLayout();
    }


}
