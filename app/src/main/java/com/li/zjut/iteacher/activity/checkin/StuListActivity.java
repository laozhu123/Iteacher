package com.li.zjut.iteacher.activity.checkin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class StuListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.check_stu_list), true);

        initView();
    }

    private void initView() {
    }
}
