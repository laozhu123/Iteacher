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
import com.li.zjut.iteacher.activity.checkin.newactivity.CheckInfoActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckAllClassesExpandAdapter;
import com.li.zjut.iteacher.adapter.checkin.CheckTimeExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckPoint;
import com.li.zjut.iteacher.bean.checkin.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.utils.CommonUtils;

public class SelectCheckPointActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRef;
    private TextView txNoContent;
    private ExpandableListView expLv;
    private List<DateTime> mDatas = new ArrayList<>();
    private CheckTimeExpandAdapter adapter;
    private int expandIndex = -1;   //记录展开是哪项


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_check_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.select_check_time), true);

        initView();
        getData();
    }

    private void initView() {

        swipeRef = (SwipeRefreshLayout) findViewById(R.id.swipeRef);
        swipeRef.setOnRefreshListener(this);
        txNoContent = (TextView) findViewById(R.id.txNoContent);
        expLv = (ExpandableListView) findViewById(R.id.expLv);
        expLv.setGroupIndicator(null);
        expLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivity(new Intent(SelectCheckPointActivity.this, CheckInfoActivity.class));
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

        data.add(new DateTime());

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
            adapter.setOnSelectListener(getListener());
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

    private CheckTimeExpandAdapter.OnSelectListener getListener() {
        return new CheckTimeExpandAdapter.OnSelectListener() {
            @Override
            public void onSelect(int index) {
                if (index == expandIndex && expLv.isGroupExpanded(index)) {
                    expLv.collapseGroup(index);
                    expandIndex = -1;
                    return;
                }
                if (expandIndex != index && expandIndex != -1)
                    expLv.collapseGroup(expandIndex);
                expLv.expandGroup(index);
                expandIndex = index;
            }
        };
    }
}