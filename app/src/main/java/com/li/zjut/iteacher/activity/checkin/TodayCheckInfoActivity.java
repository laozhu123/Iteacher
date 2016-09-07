package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckInfoExpandAdapter;
import com.li.zjut.iteacher.adapter.checkin.CheckPointExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckPoint;
import com.li.zjut.iteacher.bean.checkin.Curriculum;
import com.li.zjut.iteacher.bean.checkin.Student;
import com.li.zjut.iteacher.bean.checkin.ThreeType;

import java.util.ArrayList;
import java.util.List;

public class TodayCheckInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRef;
    private TextView txNoContent;
    private ExpandableListView expLv;
    private List<Curriculum> mDatas = new ArrayList<>();
    private CheckPointExpandAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_check_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.check_list), true);


        initView();
        getData();
    }

    private void initView() {

        swipeRef = (SwipeRefreshLayout) findViewById(R.id.swipeRef);
        swipeRef.setOnRefreshListener(this);
        txNoContent = (TextView) findViewById(R.id.txNoContent);
        expLv = (ExpandableListView) findViewById(R.id.expLv);
        expLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivity(new Intent(TodayCheckInfoActivity.this,OneCheckInfoActivity.class));
                return true;
            }
        });

    }

    private void getData() {

        if (swipeRef.isRefreshing()){
            swipeRef.setRefreshing(false);
        }
        CheckPoint s1 = new CheckPoint();
        s1.setTime("12:30");

        List<Curriculum> data = new ArrayList<>();
        List<CheckPoint> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s1);

        Curriculum t1 = new Curriculum();
        t1.setName("Java");
        t1.setList(l1);
        data.add(t1);

        Curriculum t2 = new Curriculum();
        t2.setName("操作系统");
        t2.setList(l1);
        data.add(t2);

        Curriculum t3 = new Curriculum();
        t3.setName("Java");
        t3.setList(l1);
        data.add(t3);

        if (data.size() != 0) {
            Log.d("one","show");
            expLv.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new CheckPointExpandAdapter(this,mDatas);
            expLv.setAdapter(adapter);
            expLv.setVisibility(View.VISIBLE);
            txNoContent.setVisibility(View.GONE);
        }else{
            expLv.setVisibility(View.GONE);
            txNoContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {

        getData();
    }
}