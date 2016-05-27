package com.li.zjut.iteacher.activity.checkIn;

import android.os.Bundle;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class CheckInActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.checkIn), true);


        initView();

    }

    private void initView() {


    }



}
