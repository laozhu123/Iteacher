package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.checkin.LessonListAdapter;
import com.li.zjut.iteacher.bean.checkin.Lesson;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TodayLessonsActivity extends BaseActivity {

    private RecyclerView mCycleView;
    private TextView mTvDecr;
    private List<Lesson> mDatas = new ArrayList<>();
    private LessonListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_lessons);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.todayLesson), true);

        initView();
        initData();
    }

    private void initData() {

        getFromNetWork();
        userTestData();  //使用测试数据
    }


    private void userTestData() {

        for (int i = 0;i < 5;i++){
            Lesson l = new Lesson();
            l.setId(i);
            l.setName("helo"+i);
            mDatas.add(l);
        }
        mAdapter.notifyDataSetChanged();
    }


    /*
    * 获取后台数据
    * */
    private void getFromNetWork() {


    }

    private void initView() {

        mCycleView = (RecyclerView) findViewById(R.id.reCycleView);
        mTvDecr = (TextView) findViewById(R.id.noContext);

        mAdapter = new LessonListAdapter(mDatas);
        mCycleView.setAdapter(mAdapter);
        mCycleView.setLayoutManager(new LinearLayoutManager(this));

        mCycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mCycleView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new LessonListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String school) {

                startActivity(new Intent(TodayLessonsActivity.this, CheckActivity.class)
                        .putExtra("lesson", mDatas.get(position)));
            }
        });
    }
}
