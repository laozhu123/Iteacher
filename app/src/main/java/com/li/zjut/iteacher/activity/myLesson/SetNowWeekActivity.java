package com.li.zjut.iteacher.activity.myLesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.widget.CustomDialog;
import com.li.zjut.iteacher.widget.common.WheelView;

import java.util.Arrays;

public class SetNowWeekActivity extends BaseActivity implements View.OnClickListener {

    TextView tvWeek;
    private String[] weeks;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_week);

        initView();
        initData();
    }


    private void initView() {
        findViewById(R.id.ll_btn).setOnClickListener(this);
        tvWeek = (TextView) findViewById(R.id.index_of_week);
        if (getIntent().getIntExtra("weekNum", -1) != -1) {
            index = getIntent().getIntExtra("weekNum", -1) - 1;
            tvWeek.setText("第" + getIntent().getIntExtra("weekNum", -1) + "周");
        }

        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setImageResource(R.drawable.backarrow);
        leftImg.setOnClickListener(this);
        leftImg.setVisibility(View.VISIBLE);

        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(getString(R.string.select_now_week));
    }

    private void initData() {
        weeks = new String[25];
        for (int i = 1; i <= 25; i++) {
            weeks[i - 1] = i + "";
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_btn:
                View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(2);
                wv.setItems(Arrays.asList(weeks));
                wv.setSeletion(index);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        index = selectedIndex - 2;
                    }
                });

                CustomDialog.Builder builder = new CustomDialog.Builder(this);
                builder.setContentView(outerView);
                builder.setTitle("选择当前周");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                        tvWeek.setText("第" + (index + 1) + "周");
                    }
                });

                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
                break;
            case R.id.left_img:
                intent.putExtra("weekNum", index + 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("weekNum", index + 1);
        setResult(RESULT_OK, intent);
        finish();
    }
}
