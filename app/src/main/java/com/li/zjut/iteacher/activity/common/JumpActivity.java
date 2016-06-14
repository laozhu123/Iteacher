package com.li.zjut.iteacher.activity.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.li.zjut.iteacher.activity.personinfo.PersonInfoActivity;
import com.li.zjut.iteacher.common.StaticData;


/*
* 用于推送界面跳转选择
* */
public class JumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断
//        Toast.makeText(this, StaticData.id,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PersonInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
