package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class AllLessonsActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mCycleView;
    private TextView mTvDecr;
    private List<Lesson> mDatas = new ArrayList<>();
    private LessonListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lessons);

        super.setContext(findViewById(R.id.toolbar), getString(R.string.allLesson), true);

        initView();
        initData();
    }


    private void initData() {

        getFromNetWork();
        userTestData();  //使用测试数据
    }

    private void userTestData() {

        Lesson l = new Lesson();
        l.setId(1);
        l.setName("C++");
        mDatas.add(l);

        Lesson l2 = new Lesson();
        l2.setId(2);
        l2.setName("Java");
        mDatas.add(l2);

        Lesson l3 = new Lesson();
        l3.setId(3);
        l3.setName("操作系统");
        mDatas.add(l3);

        mAdapter.notifyDataSetChanged();
    }


    /*
    * 获取后台数据
    * */
    private void getFromNetWork() {


    }

    private void initView() {
        TextView titleRight = (TextView) findViewById(R.id.txTitleRig);
        titleRight.setText(getString(R.string.check_select_student));
        titleRight.setOnClickListener(this);

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

                Intent intent = new Intent(AllLessonsActivity.this, SelectCheckPointActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                startActivity(new Intent(AllLessonsActivity.this, StuListActivity.class));
        }
    }
}
