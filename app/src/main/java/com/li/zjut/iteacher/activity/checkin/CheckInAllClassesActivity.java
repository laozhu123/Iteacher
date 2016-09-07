package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckAllClassesExpandAdapter;
import com.li.zjut.iteacher.adapter.checkin.CheckTimeExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckInClass;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;
import com.li.zjut.iteacher.bean.checkin.CheckPoint;
import com.li.zjut.iteacher.bean.checkin.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CheckInAllClassesActivity extends BaseActivity {

    private TextView txNoContent;
    private ExpandableListView expLv;
    private List<CheckInLesson> mDatas = new ArrayList<>();
    private CheckAllClassesExpandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_all_classes);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.select_check_point), true);

        initView();
        getData();
    }

    private void initView() {

        txNoContent = (TextView) findViewById(R.id.txNoContent);
        expLv = (ExpandableListView) findViewById(R.id.expLv);
        expLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                intent.putExtra("helo", mDatas.get(groupPosition).getLessonName() + mDatas.get(groupPosition).getList().get(childPosition).getClassName());
                setResult(RESULT_OK,intent);
                finish();
                return true;
            }
        });

    }

    private void getData() {

        CheckInClass s1 = new CheckInClass();
        s1.setClassName("一班");
        CheckInClass s2 = new CheckInClass();
        s2.setClassName("二班");

        List<CheckInLesson> data = new ArrayList<>();
        List<CheckInClass> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s2);

        CheckInLesson t1 = new CheckInLesson();
        t1.setLessonName("java");
        t1.setList(l1);
        data.add(t1);

        CheckInLesson t2 = new CheckInLesson();
        t2.setLessonName("c++");
        t2.setList(l1);
        data.add(t2);

        CheckInLesson t3 = new CheckInLesson();
        t3.setLessonName("data");
        t3.setList(l1);
        data.add(t3);

        if (data.size() != 0) {
            expLv.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new CheckAllClassesExpandAdapter(this, mDatas);
            expLv.setAdapter(adapter);
            expLv.setVisibility(View.VISIBLE);
            txNoContent.setVisibility(View.GONE);
        } else {
            expLv.setVisibility(View.GONE);
            txNoContent.setVisibility(View.VISIBLE);
        }
    }

}