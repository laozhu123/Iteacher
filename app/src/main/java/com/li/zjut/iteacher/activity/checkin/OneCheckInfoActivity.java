package com.li.zjut.iteacher.activity.checkin;

import android.database.DataSetObserver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckInfoExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.Student;
import com.li.zjut.iteacher.bean.checkin.ThreeType;

import java.util.ArrayList;
import java.util.List;

public class OneCheckInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRef;
    private TextView txNoContent;
    private ExpandableListView expLv;
    private List<ThreeType> mDatas = new ArrayList<>();
    private CheckInfoExpandAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_check_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.checkinfo), true);


        initView();
        getData();
    }

    private void initView() {

        swipeRef = (SwipeRefreshLayout) findViewById(R.id.swipeRef);
        swipeRef.setOnRefreshListener(this);
        txNoContent = (TextView) findViewById(R.id.txNoContent);
        expLv = (ExpandableListView) findViewById(R.id.expLv);

    }

    private void getData() {

        if (swipeRef.isRefreshing()){
            swipeRef.setRefreshing(false);
        }
        Student s1 = new Student();
        s1.setId("1");
        s1.setName("xiaowang");

        List<ThreeType> data = new ArrayList<>();
        ThreeType t1 = new ThreeType();
        t1.setType("已签到");
        List<Student> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s1);
        t1.setList(l1);
        data.add(t1);
        ThreeType t2 = new ThreeType();
        t2.setType("未签到");
        t2.setList(l1);
        data.add(t2);
        ThreeType t3 = new ThreeType();
        t3.setType("超出范围");
        t3.setList(l1);
        data.add(t3);

        if (data.size() != 0) {
            Log.d("one","show");
            expLv.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new CheckInfoExpandAdapter(this,mDatas);
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
