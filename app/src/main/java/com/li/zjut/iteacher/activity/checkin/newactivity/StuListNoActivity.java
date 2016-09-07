package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.adapter.StuCheckInfoListAdapter;
import com.li.zjut.iteacher.activity.checkin.adapter.StuCheckInfoNoListAdapter;
import com.li.zjut.iteacher.activity.checkin.bean.StuCheckBean;
import com.li.zjut.iteacher.activity.imteacher.StudentInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class StuListNoActivity extends BaseActivity {

    ListView lvStu;
    List<StuCheckBean> mDatas = new ArrayList<>();
    StuCheckInfoNoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list_new);
        super.setContext(findViewById(R.id.toolbar),getIntent().getStringExtra("type"),true);
        initView();
        initData();
    }

    private void initData() {
        StuCheckBean b1 = new StuCheckBean();
        b1.setName("老王和");
        mDatas.add(b1);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        lvStu = (ListView) findViewById(R.id.lv_stu);
        adapter = new StuCheckInfoNoListAdapter(mDatas,this);
        lvStu.setAdapter(adapter);
        lvStu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StuListNoActivity.this, StudentInfoActivity.class);
                intent.putExtra("id",1516);
                startActivity(intent);
            }
        });
    }
}
