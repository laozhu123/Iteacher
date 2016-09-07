package com.li.zjut.iteacher.activity.imteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class StudentInfoActivity extends BaseActivity implements View.OnClickListener {

    int studentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.teacher_stu_info), true);
        initView();
        initData();
    }

    private void initView() {
        TextView txTitleRig = (TextView) findViewById(R.id.txTitleRig);
        txTitleRig.setText(getString(R.string.send_message));
        txTitleRig.setVisibility(View.VISIBLE);
        txTitleRig.setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.send_mail).setOnClickListener(this);
    }

    private void initData() {
        studentId = getIntent().getIntExtra("id", -1);
        if (studentId != -1){
            /*获取学生信息*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                if (studentId != -1){
                    /*联系学生，打开IM*/

                }
                break;
            case R.id.call:
                break;
            case R.id.send_mail:
                break;
            default:
                break;
        }
    }
}
