package com.li.zjut.iteacher.activity.checkin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkin.bean.Place;
import com.li.zjut.iteacher.bean.message.CommentContent;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class PlaceListAdapter extends BaseAdapter {

    private List<Place> list;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClick onItemClick;
    private View showImg;
    private int showIndex = 0;
    private OnSelectListener onSelectListener;

    public PlaceListAdapter(List<Place> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.place_item,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.tvPlaceDetail = (TextView) convertView.findViewById(R.id.tv_place_detail);
            holder.tvPlaceName = (TextView) convertView.findViewById(R.id.tv_place_name);
            holder.imgSelect = (ImageView) convertView.findViewById(R.id.img_select);
            holder.rlItem = convertView.findViewById(R.id.rl_item);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }
        holder.tvPlaceName.setText(list.get(position).getName());
        holder.tvPlaceDetail.setText(list.get(position).getDetail());
        holder.imgSelect.setVisibility(View.GONE);
        holder.imgSelect.setTag(position);
        holder.rlItem.setOnClickListener(listener);
        if (position == showIndex) {
            showImg = holder.imgSelect;
            showImg.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    final class ViewHolder {
        public TextView tvPlaceName, tvPlaceDetail;
        public ImageView imgSelect;
        public View rlItem;
    }

    public interface OnItemClick {
        void onclick(int id);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!v.findViewById(R.id.img_select).getTag().toString().equals(showIndex + "")) {
                v.findViewById(R.id.img_select).setVisibility(View.VISIBLE);
                showImg.setVisibility(View.GONE);
                showImg = v.findViewById(R.id.img_select);
                showIndex = Integer.parseInt(v.findViewById(R.id.img_select).getTag().toString());
                onSelectListener.onSelect(showIndex);
            }
        }
    };


    public void setOnSelectListener(OnSelectListener onSelectListener){
        this.onSelectListener = onSelectListener;
    }

    public static interface OnSelectListener {
        public void onSelect(int index);
    }
}
