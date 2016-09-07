package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.SelectCheckPointActivity;
import com.li.zjut.iteacher.adapter.checkin.CheckAllClassesExpandAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckInClass;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;

import java.util.ArrayList;
import java.util.List;

public class SelectCurActivity extends BaseActivity {
    private ExpandableListView expandLv;
    private CheckAllClassesExpandAdapter adapter;
    private List<CheckInLesson> mDatas = new ArrayList<>();
    private int expandIndex = -1;   //记录展开是哪项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cur);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.select_cur), true);
        initView();
        getData();
    }

    private void initView() {
        expandLv = (ExpandableListView) findViewById(R.id.expand_lv);
        expandLv.setGroupIndicator(null);
        expandLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                intent.putExtra("lesson",mDatas.get(groupPosition));
                intent.putExtra("class", mDatas.get(groupPosition).getList().get(childPosition));
                setResult(RESULT_OK,intent);
                finish();
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
}
