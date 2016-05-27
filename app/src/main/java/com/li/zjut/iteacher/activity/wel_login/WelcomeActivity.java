package com.li.zjut.iteacher.activity.wel_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.Utils;


/*
* user:laozhu
* time:2016/5/12
* dec:欢迎界面
* */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        initView();
    }


    private void initView() {

        //渐变动画
        final Animation alpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        ImageView img = (ImageView) findViewById(R.id.img);
        img.startAnimation(alpha);

        //判断跳转到哪个界面
        img.postDelayed(new Runnable() {
            @Override
            public void run() {
                judge();
            }
        }, 1700);

    }

    private void judge() {


        if (!Utils.getVersion(this).equals(getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.VERSION, " "))) {
            //版本号不相等跳转到introduction界面
            startActivity(new Intent(this, IntroActivity.class));
            getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit().putString(SharePerfrence.VERSION, Utils.getVersion(this)).apply();
        } else if (getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getBoolean(SharePerfrence.FIRSTTIME, true)) {
            //首次进入跳转到introduction界面
            startActivity(new Intent(this, IntroActivity.class));
        } else {
            if (getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getBoolean(SharePerfrence.UNLOGIN, true)) {
                //还没有登录过跳转到登录页面
                startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                //已经登录过了跳转到首页面
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME));
            }
        }
        finish();
    }
}
