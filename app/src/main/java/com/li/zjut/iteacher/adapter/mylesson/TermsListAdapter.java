package com.li.zjut.iteacher.adapter.mylesson;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.mylesson.Terms;

import java.util.List;

/**
 * Created by LaoZhu on 2016/6/6.
 */
public class TermsListAdapter extends BaseAdapter {

    private List<Terms> list;
    private LayoutInflater mInflater;
    private Context context;
    private String selectYear = "";
    private int selectTerm = 1;
    private OnClickListener listener;

    public TermsListAdapter(List<Terms> list, Context context) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setSelectYear(String selectYear) {
        this.selectYear = selectYear;
    }

    public void setSelectTerm(int selectTerm) {
        this.selectTerm = selectTerm;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
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
            convertView = mInflater.inflate(R.layout.item_term,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.tvTitle1 = (TextView) convertView.findViewById(R.id.tv_term_title_1);
            holder.tvTitle2 = (TextView) convertView.findViewById(R.id.tv_term_title_2);
            holder.termYear = (TextView) convertView.findViewById(R.id.term_year);
            holder.imgTable1 = convertView.findViewById(R.id.img_table_1);
            holder.imgTable2 = convertView.findViewById(R.id.img_table_2);
            holder.imgSelectBtn1 = convertView.findViewById(R.id.img_select_btn_1);
            holder.imgSelectBtn2 = convertView.findViewById(R.id.img_select_btn_2);
            holder.imgTable1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectYear = list.get(position).getYear();
                    Log.d("year", selectYear);
                    selectTerm = 1;
                    listener.onClick(position, 0);
                }
            });
            holder.imgTable2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectYear = list.get(position).getYear();
                    Log.d("year", selectYear);
                    selectTerm = 2;
                    listener.onClick(position, 1);
                }
            });
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象                  }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        }

        holder.termYear.setText(list.get(position).getYear() + "学年");
        if (list.get(position).getList().size() == 2) {
            if (list.get(position).getYear().equals(selectYear)) {
                if (selectTerm == 1) {
                    holder.imgSelectBtn1.setVisibility(View.VISIBLE);
                    holder.imgSelectBtn2.setVisibility(View.GONE);
                } else {
                    holder.imgSelectBtn1.setVisibility(View.GONE);
                    holder.imgSelectBtn2.setVisibility(View.VISIBLE);
                }
            } else {
                holder.imgSelectBtn1.setVisibility(View.GONE);
                holder.imgSelectBtn2.setVisibility(View.GONE);
            }
            holder.imgTable2.setVisibility(View.VISIBLE);
            holder.tvTitle2.setVisibility(View.VISIBLE);

            holder.tvTitle1.setText(list.get(position).getList().get(0).getName());
            holder.tvTitle2.setText(list.get(position).getList().get(1).getName());
        } else {
            holder.imgTable2.setVisibility(View.GONE);
            holder.tvTitle2.setVisibility(View.GONE);
            holder.imgSelectBtn2.setVisibility(View.GONE);
            if (list.get(position).getYear().equals(selectYear)) {
                holder.imgSelectBtn1.setVisibility(View.VISIBLE);
            } else {
                holder.imgSelectBtn1.setVisibility(View.GONE);
            }
            holder.tvTitle1.setText(list.get(position).getList().get(0).getName());
        }

        return convertView;
    }

    final class ViewHolder {
        public TextView tvTitle1, tvTitle2, termYear;
        public View imgTable1, imgTable2, imgSelectBtn1, imgSelectBtn2;
    }

    public interface OnClickListener {
        void onClick(int year, int term);
    }

}
