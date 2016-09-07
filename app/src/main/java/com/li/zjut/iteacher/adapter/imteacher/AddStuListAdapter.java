package com.li.zjut.iteacher.adapter.imteacher;

/**
 * Created by LaoZhu on 2016/6/27.
 */
import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.adapter.common.ZTBaseAdapter;
import com.li.zjut.iteacher.common.ImageLoader;
import com.li.zjut.iteacher.common.Utils;
import com.li.zjut.iteacher.widget.common.HeadPhotoView;


public class AddStuListAdapter extends ZTBaseAdapter {

    ArrayList<HashMap<String, Object>> data;
    Context context;
    ImageLoader imageLoader;

    public AddStuListAdapter(Context context,
                             ArrayList<HashMap<String, Object>> data) {
        super(context, data);
        this.context = context;
        this.data = data;
        imageLoader = new ImageLoader(context);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.add_teamate_list_item, null);
            holder.id = (TextView)convertView.findViewById(R.id.id);
            holder.cheakBox = (ImageView)convertView.findViewById(R.id.check_box);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.phone = (TextView)convertView.findViewById(R.id.phone);
            holder.photo = (HeadPhotoView)convertView.findViewById(R.id.photo);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if((Boolean)data.get(position).get("isCheck")){
            holder.cheakBox.setImageDrawable(context.getResources().getDrawable(R.drawable.check_gou));
        }
        else{
            holder.cheakBox.setImageDrawable(context.getResources().getDrawable(R.drawable.check_kong));
        }
        holder.id.setText((String)data.get(position).get("id"));
        holder.name.setText((String)data.get(position).get("name"));
        holder.phone.setText((String)data.get(position).get("phone"));

        String iconurl = (String)data.get(position).get("photo");
        String url = Utils.clipURL(iconurl);
        imageLoader.DisplayImage(url, (Activity)context, holder.photo);

        return convertView;
    }

    static class ViewHolder{
        TextView id;
        TextView name;
        TextView phone;
        HeadPhotoView photo;
        ImageView cheakBox;
    }
}