package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.adapter.CheckInfoListAdapter;
import com.li.zjut.iteacher.activity.checkin.bean.CheckBean;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.utils.CommonUtils;

public class CheckInfoActivity extends BaseActivity {

    TextView tvTime, tvDate;
    ListView lvInfo;
    List<CheckBean> mDatas = new ArrayList<>();
    CheckInfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.checkinfo), true);
        initView();
        initData();
    }

    private void initData() {
        tvDate.setText("2016年8月4日 星期四");
        tvTime.setText("09:00");

        CheckBean cb = new CheckBean();
        cb.setType("迟到");
        cb.setPeopleTypeNum(3);
        cb.setPeopleAllNum(15);
        mDatas.add(cb);

        CheckBean cb1 = new CheckBean();
        cb1.setType("准时");
        cb1.setPeopleTypeNum(3);
        cb1.setPeopleAllNum(15);
        mDatas.add(cb1);

        CheckBean cb2 = new CheckBean();
        cb2.setType("未签到");
        cb2.setPeopleTypeNum(3);
        cb2.setPeopleAllNum(15);
        mDatas.add(cb2);

        CheckBean cb3 = new CheckBean();
        cb3.setType("范围外");
        cb3.setPeopleTypeNum(3);
        cb3.setPeopleAllNum(15);
        mDatas.add(cb3);

        adapter.notifyDataSetChanged();

    }

    private void initView() {

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        adapter = new CheckInfoListAdapter(mDatas, this);
        lvInfo.setAdapter(adapter);

        lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mDatas.get(position).getType().equals("未签到")) {
                    Intent intent = new Intent(CheckInfoActivity.this, StuListNoActivity.class);
                    intent.putExtra("type", mDatas.get(position).getType());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CheckInfoActivity.this, StuListNewActivity.class);
                    intent.putExtra("type", mDatas.get(position).getType());
                    startActivity(intent);
                }
            }
        });
    }


}
