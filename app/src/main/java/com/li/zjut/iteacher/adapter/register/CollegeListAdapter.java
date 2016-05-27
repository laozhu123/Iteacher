package com.li.zjut.iteacher.adapter.register;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.register.College;

import java.util.List;

/**
 * Created by LaoZhu on 2016/4/27.
 */
public class CollegeListAdapter extends RecyclerView.Adapter<CollegeListAdapter.ViewHolder> {

    private List<College> mDatas;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public CollegeListAdapter(List<College> datas) {
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
        holder.textView.setText(mDatas.get(position).getCollegename());
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
                        listener.onClick(v, getLayoutPosition(), mDatas.get(getLayoutPosition()).getCollegename());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, String city);
    }
}

