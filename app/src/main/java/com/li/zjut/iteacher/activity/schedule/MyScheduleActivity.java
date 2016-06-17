package com.li.zjut.iteacher.activity.schedule;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.OneCheckInfoActivity;
import com.li.zjut.iteacher.adapter.schedule.ScheduleListAdapter;
import com.li.zjut.iteacher.bean.test.Com;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;
import com.li.zjut.iteacher.widget.calendar.KCalendar;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyScheduleActivity extends BaseActivity implements View.OnClickListener {

    String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式
    private RecyclerView reCycleView;
    private List<Com> mData = new ArrayList<>();
    private ScheduleListAdapter mAdapter;

    private PopupWindow pop = null;
    private View mHidden;
    private TextView noContext;
    private TextView popTxtTitle;
    private ImageView rig_img;

    KCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.myschdule), true);

        initView();
        calendar();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pop();
                getTodayData();
            }
        }, 1000);


        initData();
    }

    android.os.Handler handler = new android.os.Handler();


    private void initData() {

        getFromDB();

    }

    private void getTodayData() {

        for (int i = 1; i < 10; i++) {
            Com com = new Com();
            com.setText("helo");
            mData.add(com);
        }
        mAdapter.notifyDataSetChanged();
        reCycleView.setVisibility(View.VISIBLE);
        noContext.setVisibility(View.GONE);
    }

    private void showPop(String date) {

        popTxtTitle.setText(date);
        pop.showAtLocation((View) mHidden.getParent(), Gravity.CENTER, 0, 0);
        findViewById(R.id.transparent).setVisibility(View.VISIBLE);
    }

    private void pop() {
        View cont = LayoutInflater.from(this)
                .inflate(R.layout.popup_schedule_list, null);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
        pop = new PopupWindow(cont, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.popupwindow));

        reCycleView = (RecyclerView) cont.findViewById(R.id.reCycleView);
        reCycleView.setLayoutManager(new LinearLayoutManager(this));
        reCycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        reCycleView.setItemAnimator(new DefaultItemAnimator());

        noContext = (TextView) cont.findViewById(R.id.noContext);
        noContext.setText(getString(R.string.nodata));
        mAdapter = new ScheduleListAdapter(mData);
        mAdapter.setOnItemClickListener(new ScheduleListAdapter.OnItemClickListener() {

            @Override
            public void onClick(View v, int position) {
               pop.dismiss();
            }
        });
        reCycleView.setAdapter(mAdapter);

        popTxtTitle = (TextView) cont.findViewById(R.id.title);
        popTxtTitle.setText(getString(R.string.todaytask));

        cont.findViewById(R.id.create).setOnClickListener(this);
        cont.findViewById(R.id.close).setOnClickListener(this);

    }

    private void getFromDB() {

        List<String> list = new ArrayList<String>(); //设置标记列表
        list.add("2014-04-01");
        list.add("2014-04-02");
        list.add("2016-06-16");
        calendar.addMarks(list, 0);
        Calendar c = Calendar.getInstance();
        calendar.setCalendarDayBgColor(c.getTime(), R.drawable.calendar_date_focused);

    }

    private void initView() {

        //先设置日历
        mHidden = findViewById(R.id.hidden);
        findViewById(R.id.transparent).setOnClickListener(this);
        rig_img = (ImageView) findViewById(R.id.right_img);
        rig_img.setImageResource(R.drawable.icon_add);
        rig_img.setOnClickListener(this);
    }

    private void calendar() {

        final TextView popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
        calendar = (KCalendar) findViewById(R.id.popupwindow_calendar);

        popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
                + calendar.getCalendarMonth() + "月");

        if (null != date) {

            int years = Integer.parseInt(date.substring(0,
                    date.indexOf("-")));
            int month = Integer.parseInt(date.substring(
                    date.indexOf("-") + 1, date.lastIndexOf("-")));
            popupwindow_calendar_month.setText(years + "年" + month + "月");

            calendar.showCalendar(years, month);
            calendar.setCalendarDayBgColor(date,
                    R.drawable.calendar_date_focused);
        }


        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    calendar.removeAllBgColor();
                    calendar.setCalendarDayBgColor(dateFormat, R.drawable.calendar_date_focused);
                    date = dateFormat;//最后返回给全局 date

                    showPop(date);


                }
            }
        });

        //监听当前月份
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month
                        .setText(year + "年" + month + "月");
            }
        });

        //上月监听按钮
        RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) findViewById(R.id.popupwindow_calendar_last_month);
        popupwindow_calendar_last_month
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        calendar.lastMonth();
                    }

                });

        //下月监听按钮
        RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) findViewById(R.id.popupwindow_calendar_next_month);
        popupwindow_calendar_next_month
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        calendar.nextMonth();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                startActivity(new Intent(getApplication(), CreateSchedulectivity.class));
                break;
            case R.id.close:
            case R.id.transparent:
                pop.dismiss();
                findViewById(R.id.transparent).setVisibility(View.GONE);
                break;
            case R.id.right_img:
                startActivity(new Intent(getApplication(), CreateSchedulectivity.class));
        }
    }

    @Override
    public void onBackPressed() {

        if (pop.isShowing()) {
            pop.dismiss();
            findViewById(R.id.transparent).setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }


}
