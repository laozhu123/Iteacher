package com.li.zjut.iteacher.adapter.register;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.register.Campus;

import java.util.List;

/**
 * Created by LaoZhu on 2016/5/24.
 */
public class CampusListAdapter extends RecyclerView.Adapter<CampusListAdapter.ViewHolder>  {

    private List<Campus> mDatas;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public CampusListAdapter(List<Campus> datas) {
        mDatas = datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.allschool_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position).getCampusname());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.helo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v, getLayoutPosition(), mDatas.get(getLayoutPosition()).getCampusname());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, String city);
    }
}
