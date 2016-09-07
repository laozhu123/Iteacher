package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.CheckInAllClassesActivity;
import com.li.zjut.iteacher.activity.checkin.SelectCheckPointActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckAllClassesExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckInClass;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;

import java.util.ArrayList;
import java.util.List;

public class CheckHistoryActivity extends BaseActivity implements View.OnClickListener{

    private ExpandableListView expandLv;
    private TextView tvLastCheck;
    private CheckAllClassesExpandAdapter adapter;
    private List<CheckInLesson> mDatas = new ArrayList<>();
    private int expandIndex = -1;   //记录展开是哪项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.history_check), true);
        initView();
        getData();
    }

    private void initView() {
        findViewById(R.id.tv_last_check).setOnClickListener(this);
        expandLv = (ExpandableListView) findViewById(R.id.expand_lv);
        expandLv.setGroupIndicator(null);
        tvLastCheck = (TextView) findViewById(R.id.tv_last_check);
        expandLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                intent.setClass(CheckHistoryActivity.this, SelectCheckPointActivity.class);
                intent.putExtra("id", mDatas.get(groupPosition).getList().get(childPosition).getId());
                startActivity(intent);
                return true;
            }
        });
    }

    private void getData() {

        CheckInClass s1 = new CheckInClass();
        s1.setClassName("一班");
        s1.setClassDetail("周三 3-4节");
        s1.setId("1");
        CheckInClass s2 = new CheckInClass();
        s2.setClassName("二班");
        s2.setClassDetail("周一 1-4节");
        s2.setId("2");

        List<CheckInLesson> data = new ArrayList<>();
        List<CheckInClass> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s2);


        CheckInLesson t1 = new CheckInLesson();
        t1.setLessonName("java");
        t1.setList(l1);
        data.add(new CheckInLesson());
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
            expandLv.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new CheckAllClassesExpandAdapter(this, mDatas);
            adapter.setOnSelectListener(getListener());
            expandLv.setAdapter(adapter);
            expandLv.setVisibility(View.VISIBLE);
        }
    }

    private CheckAllClassesExpandAdapter.OnSelectListener getListener() {
        return new CheckAllClassesExpandAdapter.OnSelectListener() {
            @Override
            public void onSelect(int index) {
                if (index == expandIndex && expandLv.isGroupExpanded(index)){
                    expandLv.collapseGroup(index);
                    expandIndex = -1;
                    return;
                }
                if (expandIndex != index && expandIndex != -1)
                    expandLv.collapseGroup(expandIndex);
                expandLv.expandGroup(index);
                expandIndex = index;
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_last_check:
                Intent intent = new Intent(CheckHistoryActivity.this,CheckInfoActivity.class);
                intent.putExtra("id",123);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
