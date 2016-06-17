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
import com.li.zjut.iteacher.adapter.checkin.CheckPointExpandAdapter;
import com.li.zjut.iteacher.adapter.checkin.CheckTimeExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckPoint;
import com.li.zjut.iteacher.bean.checkin.Curriculum;
import com.li.zjut.iteacher.bean.checkin.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SelectCheckPointActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRef;
    private TextView txNoContent;
    private ExpandableListView expLv;
    private List<DateTime> mDatas = new ArrayList<>();
    private CheckTimeExpandAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_check_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.select_check_point), true);

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
                startActivity(new Intent(SelectCheckPointActivity.this, OneCheckInfoActivity.class));
                return true;
            }
        });

    }

    private void getData() {

        if (swipeRef.isRefreshing()) {
            swipeRef.setRefreshing(false);
        }
        CheckPoint s1 = new CheckPoint();
        s1.setTime("12:30");

        List<DateTime> data = new ArrayList<>();
        List<CheckPoint> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s1);

        DateTime t1 = new DateTime();
        t1.setDate("12/30");
        t1.setList(l1);
        data.add(t1);

        DateTime t2 = new DateTime();
        t2.setDate("12/12");
        t2.setList(l1);
        data.add(t2);

        DateTime t3 = new DateTime();
        t3.setDate("12/01");
        t3.setList(l1);
        data.add(t3);

        if (data.size() != 0) {
            Log.d("one", "show");
            expLv.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new CheckTimeExpandAdapter(this, mDatas);
            expLv.setAdapter(adapter);
            expLv.setVisibility(View.VISIBLE);
            txNoContent.setVisibility(View.GONE);
        } else {
            expLv.setVisibility(View.GONE);
            txNoContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {

        getData();
    }
}