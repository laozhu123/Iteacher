package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.imteacher.ImteacherTaskListAdapter;
import com.li.zjut.iteacher.bean.imteacher.Imteacher_task;

import java.util.ArrayList;
import java.util.List;

public class StuInfoActivity extends BaseActivity implements View.OnClickListener {

    View add;
    ListView lvTask;
    ImteacherTaskListAdapter adapter;
    List<Imteacher_task> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_info);
        super.setContext(findViewById(R.id.toolbar), "xiaohong" + getString(R.string.teacher_task_list), true);

        initView();
        getDataFromNetwork();
    }

    private void getDataFromNetwork() {

        mDatas.add(new Imteacher_task("2016/8/6", "helo", "nice"));
        mDatas.add(new Imteacher_task("2016/8/6", "helo", "nice"));
    }

    private void initView() {

        add = findViewById(R.id.add);
        lvTask = (ListView) findViewById(R.id.lv_task);
        add.setOnClickListener(this);
        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(StuInfoActivity.this, ImTeacherTaskDetailActivity.class));
            }
        });
        adapter = new ImteacherTaskListAdapter(mDatas, this);
        lvTask.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                startActivity(new Intent(StuInfoActivity.this, ImteacherNewTaskActivity.class));
                break;
        }
    }
}
