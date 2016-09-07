package com.li.zjut.iteacher.activity.myLesson;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.mylesson.WeekSelectListAdapter;
import com.li.zjut.iteacher.bean.mylesson.Curriculum;
import com.li.zjut.iteacher.common.DateUtils;
import com.li.zjut.iteacher.common.DensityUtil;
import com.li.zjut.iteacher.common.StaticData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MyLessonActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout back; //背景图片设置
    int x = 0, y = 0; //每个课程View的宽高
    List<Curriculum> mDatas = new ArrayList<>(); //课程信息
    List<View> mViews = new ArrayList<>();//存放课程view，用于清空界面时使用
    List<View> mWeekdayViews = new ArrayList<>(); //存放每一天的容器，用于改变背景颜色，设置当天
    List<TextView> mWeekdayTvs = new ArrayList<>();  //每天的日期设置
    TextView tvMonth, tvWeekNum, tvTerm;//月份，第几周，学期
    View alphaToolbar, llSelectWeek;
    ListView lvWeeks;
    WeekSelectListAdapter adapter;
    List<String> mDataSelects = new ArrayList<>();
    String firstWeekDay;
    int weekNum;   //现在是第几周
    int weekNumShow;  //选择的是第几周
    SharedPreferences sp;
    final int REQUEST_SET_NOW_WEEK = 1, REQUEST_SET_TERM = 2;
    ImageView imgTriangle;
    ImageView imgRight;

    View alphaAddTouch, rlAddTouchBg, llAddCourse, rlSettingTerm, rlSettingWeekNum;
    TextView tvSettingTerm, tvSettingWeekNum;
    String nowTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_cur_table_activity);

        initView();
        initData();
    }

    private void initView() {

        alphaAddTouch = findViewById(R.id.alpha_add_touch);
        rlAddTouchBg = findViewById(R.id.rl_add_touch_bg);
        llAddCourse = findViewById(R.id.ll_add_course);
        rlSettingTerm = findViewById(R.id.rl_setting_term);
        rlSettingWeekNum = findViewById(R.id.rl_setting_week_num);
        tvSettingTerm = (TextView) findViewById(R.id.tv_setting_term);
        tvSettingWeekNum = (TextView) findViewById(R.id.tv_setting_week_num);
        alphaAddTouch.setOnClickListener(this);
        rlAddTouchBg.setOnClickListener(this);
        llAddCourse.setOnClickListener(this);
        rlSettingWeekNum.setOnClickListener(this);
        rlSettingTerm.setOnClickListener(this);

        back = (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        imgRight = (ImageView) findViewById(R.id.img_right);
        imgRight.setOnClickListener(this);
        findViewById(R.id.v_back).setOnClickListener(this);
        findViewById(R.id.modify_now_week).setOnClickListener(this);
        imgTriangle = (ImageView) findViewById(R.id.img_triangle);

        alphaToolbar = findViewById(R.id.alpha_toolbar);
        alphaToolbar.setOnClickListener(this);
        llSelectWeek = findViewById(R.id.ll_select_week);
        lvWeeks = (ListView) findViewById(R.id.lv_weeks);
        adapter = new WeekSelectListAdapter(mDataSelects, this);
        lvWeeks.setAdapter(adapter);
        lvWeeks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                llSelectWeek.setVisibility(View.GONE);
                alphaToolbar.setVisibility(View.GONE);
                weekNumShow = position + 1;
                adapter.setSelectWeek(weekNumShow);
                adapter.notifyDataSetChanged();
                if (weekNumShow == weekNum) {
                    tvWeekNum.setText("第" + weekNumShow + "周");
                    tvWeekNum.setTextColor(getResources().getColor(R.color.white));
                    imgTriangle.setImageResource(R.mipmap.arrow_down);
                } else {
                    tvWeekNum.setText("第" + weekNumShow + "周(非本周)");
                    tvWeekNum.setTextColor(getResources().getColor(R.color.yellow));
                    imgTriangle.setImageResource(R.mipmap.arrow_down_yellow);
                }

                setDate();//设置星期栏上的日期
            }
        });

        mWeekdayTvs.add((TextView) findViewById(R.id.monday));
        mWeekdayTvs.add((TextView) findViewById(R.id.tuesday));
        mWeekdayTvs.add((TextView) findViewById(R.id.wednesday));
        mWeekdayTvs.add((TextView) findViewById(R.id.thursday));
        mWeekdayTvs.add((TextView) findViewById(R.id.friday));
        mWeekdayTvs.add((TextView) findViewById(R.id.saturday));
        mWeekdayTvs.add((TextView) findViewById(R.id.sunday));

        mWeekdayViews.add(findViewById(R.id.monday_content));
        mWeekdayViews.add(findViewById(R.id.tuesday_content));
        mWeekdayViews.add(findViewById(R.id.wednesday_content));
        mWeekdayViews.add(findViewById(R.id.thursday_content));
        mWeekdayViews.add(findViewById(R.id.friday_content));
        mWeekdayViews.add(findViewById(R.id.saturday_content));
        mWeekdayViews.add(findViewById(R.id.sunday_content));
        for (View v : mWeekdayViews)
            v.setOnClickListener(this);

        tvMonth = (TextView) findViewById(R.id.month);

        findViewById(R.id.ll_week_term).setOnClickListener(this);

        tvTerm = (TextView) findViewById(R.id.term);
        tvWeekNum = (TextView) findViewById(R.id.week_num);

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        tvMonth.setText(mMonth + "月");
        //        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码

        if (c.get(Calendar.DAY_OF_WEEK) - 2 == -1) {
            mWeekdayViews.get(6).setSelected(true);
        } else {
            mWeekdayViews.get(c.get(Calendar.DAY_OF_WEEK) - 2).setSelected(true);
        }

        int[] thisWeek = DateUtils.get7day(c);//获取当前星期的日期
        for (int i = 0; i < 7; i++) {
            mWeekdayTvs.get(i).setText(thisWeek[i] + "");
        }
    }

    //获取课程数据
    private void initData() {
        for (int i = 0; i < 5; i++) {
            Curriculum curriculum = new Curriculum();
            curriculum.setBegin(i + 1);
            curriculum.setEnd(i + 1 + i);
            curriculum.setWeekday(i + 1);
            mDatas.add(curriculum);
        }
        if (x != 0) {
            addLessonView();
        }

        sp = getApplication().getSharedPreferences("helo", MODE_PRIVATE);
        nowTerm = sp.getString("nowTerm", "");
        if (nowTerm.equals("")) {
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH);
            String up_down;
            if (month >= 8)
                up_down = "下";
            else
                up_down = "上";
            String term = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.YEAR) + 1) + up_down;
            nowTerm = term;
            sp.edit().putString("nowTerm", term).apply();
        }
        tvTerm.setText(nowTerm);

        firstWeekDay = sp.getString("firstWeekDay", "");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (firstWeekDay.equals("")) {
            weekNum = 1;
            weekNumShow = 1;

            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            if (c.get(Calendar.DAY_OF_WEEK) - 2 == -1) {//为周日
                c.add(Calendar.DATE, -6);
            } else {
                c.add(Calendar.DATE, 1 - c.get(Calendar.DAY_OF_WEEK));
            }
            Date date = c.getTime();
            String helo = df.format(date);
            sp.edit().putString("firstWeekDay", helo).commit();
            weekNum = 1;
            weekNumShow = weekNum;
            tvWeekNum.setText("第" + weekNum + "周");
            tvWeekNum.setTextColor(getResources().getColor(R.color.white));
            imgTriangle.setImageResource(R.mipmap.arrow_down);
            Log.d("ll", helo);
        } else {
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            Date date = c.getTime();
            String nowDate = df.format(date);
            int weeks = 0;
            try {
                weeks = DateUtils.daysBetween(firstWeekDay, nowDate) / 7;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            weekNum = 1 + weeks;
            weekNumShow = weekNum;
            tvWeekNum.setText("第" + weekNum + "周");
            tvWeekNum.setTextColor(getResources().getColor(R.color.white));
            imgTriangle.setImageResource(R.mipmap.arrow_down);
        }
        initWeekData();
        adapter.setSelectWeek(weekNumShow);
        adapter.notifyDataSetChanged();
        lvWeeks.setSelection(weekNumShow - 1);
    }

    public void initWeekData() {
        mDataSelects.clear();
        for (int i = 1; i <= 25; i++) {
            mDataSelects.add("第" + i + "周");
        }
        mDataSelects.set(weekNum - 1, mDataSelects.get(weekNum - 1) + "(本周)");
    }


    private void setDate() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.roll(Calendar.DAY_OF_YEAR, 7 * (weekNumShow - weekNum));
        int[] thisWeek = DateUtils.get7day(c);//获取当前星期的日期
        for (int i = 0; i < 7; i++) {
            mWeekdayTvs.get(i).setText(thisWeek[i] + "");
        }
        tvMonth.setText((c.get(Calendar.MONTH) + 1) + "月");
    }


    /*
    * 当界面被添加后，获取每个课程view的宽高
    * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        x = back.getWidth() / 7;
        y = back.getHeight() / 12;
        addIcon();
        if (mDatas.size() != 0)
            addLessonView();
    }

    //    添加加号标记
    private void addIcon() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 12; j++) {
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(DensityUtil.dip2px(this, 15), DensityUtil.dip2px(this, 15));
                ImageView v = new ImageView(this);
                v.setImageResource(R.mipmap.add_icon);
                p.setMargins((i + 1) * x - DensityUtil.dip2px(this, (float) 7.5), (j + 1) * y - DensityUtil.dip2px(this, (float) 7.5), 0, 0);
                v.setLayoutParams(p);
                back.addView(v);
            }
        }
        addLessonView();
    }

    //添加课程view
    private void addLessonView() {
        int i = 0;
        for (Curriculum c : mDatas) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(x - 4, (c.getEnd() - c.getBegin() + 1) * y - 4);
            TextView tx = new TextView(this);
            lp.setMargins(x * (c.getWeekday() - 1) + 2, y * (c.getBegin() - 1) + 2, 0, 0);
            tx.setLayoutParams(lp);
            tx.setBackgroundResource(StaticData.lesson_bg[i % StaticData.lesson_bg.length]);
            tx.setAlpha((float) 0.75);
            tx.setText("C++" + "\n" + "于玲楼");

            tx.setTextColor(getResources().getColor(R.color.white));
            tx.setTextSize(14);
            tx.setGravity(Gravity.CENTER_HORIZONTAL);
            tx.setPadding(3, 3, 3, 3);
            back.addView(tx);
            mViews.add(tx);
            i++;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_week_term:
                if (alphaToolbar.getVisibility() == View.GONE) {
                    alphaToolbar.setVisibility(View.VISIBLE);
                    llSelectWeek.setVisibility(View.VISIBLE);
                } else {
                    alphaToolbar.setVisibility(View.GONE);
                    llSelectWeek.setVisibility(View.GONE);
                }
                break;
            case R.id.alpha_toolbar:
                alphaToolbar.setVisibility(View.GONE);
                llSelectWeek.setVisibility(View.GONE);
                break;
            case R.id.v_back:
                finish();
                break;
            case R.id.rl_setting_week_num:
                dismiss_add_bg(true);
            case R.id.modify_now_week:      //修改本周周数
                intent.setClass(MyLessonActivity.this, SetNowWeekActivity.class);
                intent.putExtra("weekNum", weekNum);
                startActivityForResult(intent, REQUEST_SET_NOW_WEEK);
                llSelectWeek.setVisibility(View.GONE);
                alphaToolbar.setVisibility(View.GONE);
                break;
            case R.id.img_right:        //加号转动45度
                if (rlAddTouchBg.getVisibility() == View.VISIBLE) {
                    dismiss_add_bg(true);
                } else {
                    dismiss_add_bg(false);
                }
                break;
            case R.id.rl_add_touch_bg:
                dismiss_add_bg(true);
                break;
            case R.id.rl_setting_term:
                intent.setClass(MyLessonActivity.this, SelectTermActivity.class);
                startActivityForResult(intent, REQUEST_SET_TERM);
                dismiss_add_bg(true);
                break;

            case R.id.ll_add_course:
                dismiss_add_bg(true);
                break;
        }
    }

    //    显示或隐藏点击右上角按钮后的抉择
    private void dismiss_add_bg(boolean dismiss) {
        Animation operatingAnim;
        if (dismiss) {
            rlAddTouchBg.setVisibility(View.GONE);
            alphaAddTouch.setVisibility(View.GONE);
            operatingAnim = AnimationUtils.loadAnimation(this, R.anim.add_rotation_converse);
        } else {
            rlAddTouchBg.setVisibility(View.VISIBLE);
            alphaAddTouch.setVisibility(View.VISIBLE);
            operatingAnim = AnimationUtils.loadAnimation(this, R.anim.add_rotation);
            tvSettingWeekNum.setText("第" + weekNum + "周");
            tvSettingTerm.setText(nowTerm);
        }

        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        operatingAnim.setFillAfter(true);
        if (operatingAnim != null) {
            imgRight.startAnimation(operatingAnim);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SET_NOW_WEEK:
                    if (data.getIntExtra("weekNum", -1) != -1) {
                        if (data.getIntExtra("weekNum", -1) != weekNum) {         //从选择周数界面返回时
                            weekNum = data.getIntExtra("weekNum", -1);
                            tvWeekNum.setText("第" + weekNum + "周");
                            tvWeekNum.setTextColor(getResources().getColor(R.color.white));
                            imgTriangle.setImageResource(R.mipmap.arrow_down);
                            weekNumShow = weekNum;
                            adapter.setSelectWeek(weekNumShow);
                            adapter.notifyDataSetChanged();

                            initWeekData();
                            setDate();

                            Calendar c = Calendar.getInstance();
                            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                            if (c.get(Calendar.DAY_OF_WEEK) - 2 == -1) {//为周日
                                c.add(Calendar.DATE, -6);
                            } else {
                                c.add(Calendar.DATE, 1 - c.get(Calendar.DAY_OF_WEEK));
                            }
                            c.add(Calendar.DATE, (weekNum - 1) * -7);
                            Date date = c.getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String helo = df.format(date);
                            sp.edit().putString("firstWeekDay", helo).commit();
                        }
                    }
                    break;
                case REQUEST_SET_TERM:
                    if (data.getIntExtra("id", -1) != -1) {
                        tvTerm.setText(data.getStringExtra("selectYear") + data.getStringExtra("selectTerm"));
                        nowTerm = data.getStringExtra("selectYear") + data.getStringExtra("selectTerm");
                    }
                    break;
            }
        }
    }
}
