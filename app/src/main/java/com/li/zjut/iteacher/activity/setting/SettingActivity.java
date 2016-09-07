package com.li.zjut.iteacher.activity.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.wel_login.LoginActivity;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        super.setContext(findViewById(R.id.toolbar),getString(R.string.setting),true);

        initView();
    }

    private void initView() {
        findViewById(R.id.logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                logout();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit().putBoolean(SharePerfrence.UNLOGIN, true).apply();
        startActivity(new Intent(getApplication(), LoginActivity.class));
        finish();

    }
}
