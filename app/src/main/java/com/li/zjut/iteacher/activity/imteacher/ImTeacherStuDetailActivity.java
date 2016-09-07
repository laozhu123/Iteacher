package com.li.zjut.iteacher.activity.imteacher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.personinfo.BigPicActivity;
import com.li.zjut.iteacher.common.BaseContext;
import com.li.zjut.iteacher.common.BaseContextImp;
import com.li.zjut.iteacher.widget.common.HeadPhotoView;
import com.li.zjut.iteacher.widget.personinfo.Utils;

import java.util.Timer;
import java.util.TimerTask;

import io.rong.imkit.RongIM;

public class ImTeacherStuDetailActivity extends BaseActivity {

    TextView phone, email, qq, birthday, weixin, workphone, shortphone,
            message_btn, stuDetail;
    int isInGroup;
    ImageView message, call;
    HeadPhotoView photo;

    String url;

    FrameLayout loadframe;
    ScrollView content;
    BaseContext base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_detail);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.teacher_stu_info), true);

        initView();
    }

    private void initView() {

        base = new BaseContextImp(this);

        photo = (HeadPhotoView) findViewById(R.id.photo);
        photo.setOnClickListener(listener);
        photo.setCirCleWidth(Utils.Dp2Px(this, 4));
        photo.setCirCleColor(getResources().getColor(R.color.di_se));
        email = (TextView) findViewById(R.id.email);
        qq = (TextView) findViewById(R.id.qq);
        birthday = (TextView) findViewById(R.id.birthday);
        phone = (TextView) findViewById(R.id.phone);
        workphone = (TextView) findViewById(R.id.workphone);
        shortphone = (TextView) findViewById(R.id.shortphone);
        weixin = (TextView) findViewById(R.id.weixin);
        stuDetail = (TextView) findViewById(R.id.stu_detail);
        stuDetail.setOnClickListener(listener);

        message_btn = (TextView) findViewById(R.id.message_btn);
        message_btn.setOnClickListener(listener);
        message_btn.setText("发消息");

        call = (ImageView) findViewById(R.id.call);
        message = (ImageView) findViewById(R.id.message);
        call.setOnClickListener(listener);
        message.setOnClickListener(listener);

        loadframe = (FrameLayout) findViewById(R.id.loadframe);
        content = (ScrollView) findViewById(R.id.content);
        base.addView(loadframe, content);
        base.startLoading();
        Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                content.post(new Runnable() {
                    @Override
                    public void run() {
                        base.stopLoading();
                    }
                });
            }
        }, 2000);
    }

    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.call:
                    call();
                    break;
                case R.id.message:
                    sendMessage();
                    break;
                case R.id.photo:
                    Intent intent = new Intent(ImTeacherStuDetailActivity.this, BigPicActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    break;
                case R.id.message_btn:

                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().startPrivateChat(ImTeacherStuDetailActivity.this, "17764507393", "title");
                    break;
                case R.id.stu_detail:
//                    startActivity();
                    break;
                default:
                    break;
            }
        }
    };

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone.getText()));
        startActivity(intent);
    }
}
