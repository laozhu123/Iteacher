package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class ImTeacherFirstNewActivity extends BaseActivity implements View.OnClickListener {

    TextView tvTaskNum, tvStudentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_teacher_first_new);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.im_teacher), true);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.student_list).setOnClickListener(this);
        findViewById(R.id.student_class).setOnClickListener(this);
        findViewById(R.id.task_list).setOnClickListener(this);
        findViewById(R.id.send_task).setOnClickListener(this);
        tvStudentNum = (TextView) findViewById(R.id.student_num);
        tvTaskNum = (TextView) findViewById(R.id.task_num);
    }

    private void initData() {
        tvStudentNum.setText("5人");
        tvTaskNum.setText("10个任务进行中");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_list:
                startActivity(new Intent(ImTeacherFirstNewActivity.this, StudentListActivity.class));
                break;
            case R.id.student_class:
                break;
            case R.id.task_list:
                startActivity(new Intent(ImTeacherFirstNewActivity.this,TaskListActivity.class));
                break;
            case R.id.send_task:
                startActivity(new Intent(ImTeacherFirstNewActivity.this,ImteacherNewTaskActivity.class));
                break;
        }
    }
}
