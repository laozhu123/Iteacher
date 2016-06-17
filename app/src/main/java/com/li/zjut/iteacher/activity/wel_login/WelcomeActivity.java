package com.li.zjut.iteacher.activity.wel_login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


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
//        helo();
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


    void helo(){
        String TAG = "helo";
        try {


            // 开辟一个输入流
            InputStream open = getApplication().getAssets().open("src/main/libs/armeabi/libRongIMLib.so");

            File dir = getApplication().getDir("libs", Context.MODE_PRIVATE);
            // 获取驱动文件输出流
            File soFile = new File(dir, "libRongIMLib.so");
            if (!soFile.exists()) {
                Log.v(TAG, "### " + soFile.getAbsolutePath() + " is not exists");
                FileOutputStream fos = new FileOutputStream(soFile);
                Log.v(TAG, "开写");

                // 字节数组输出流，写入到内存中(ram)
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = open.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                // 从内存到写入到具体文件
                fos.write(baos.toByteArray());
                // 关闭文件流
                baos.close();
                fos.close();
            }
            open.close();
            Log.v(TAG, "### System.load start");
            // 加载外设驱动
            System.load(soFile.getAbsolutePath());
            Log.v(TAG, "### System.load End");


        } catch (Exception e) {
            Log.v(TAG, "Exception   " + e.getMessage());
            e.printStackTrace();

        }
    }

}
