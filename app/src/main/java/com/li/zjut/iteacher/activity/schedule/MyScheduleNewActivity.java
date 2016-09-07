package com.li.zjut.iteacher.activity.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.imteacher.ImTeacherTaskDetailActivity;
import com.li.zjut.iteacher.activity.schedule.adapter.ScheduleTaskListAdapter;
import com.li.zjut.iteacher.bean.checkin.CheckInClass;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;
import com.li.zjut.iteacher.widget.calendar.CalendarView;
import com.li.zjut.iteacher.widget.calendar.ContainerLayout;
import com.li.zjut.iteacher.widget.calendar.DateBean;
import com.li.zjut.iteacher.widget.calendar.OtherUtils;
import com.li.zjut.iteacher.widget.calendar.TopViewPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyScheduleNewActivity extends BaseActivity implements View.OnClickListener {

    private ContainerLayout container;
    private TextView txToday;
    private ViewPager vpCalender;

    private List<View> calenderViews = new ArrayList<>();
    private ExpandableListView viewContent;
    private List<CheckInLesson> mDatas = new ArrayList<>();
    private ScheduleTaskListAdapter adapter;
    /**
     * 日历向左或向右可翻动的月
     */
    private int INIT_PAGER_INDEX = 120;

    private int expandIndex = 1;   //记录展开是哪项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule_new);

        initView();
        initData();
    }

    private void initView() {
        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setImageResource(R.mipmap.back_arrow_white);
        leftImg.setOnClickListener(this);
        leftImg.setVisibility(View.VISIBLE);

        ImageView rigImg = (ImageView) findViewById(R.id.right_img);
        rigImg.setImageResource(R.mipmap.ic_navigation_add_normal);
        rigImg.setOnClickListener(this);
        rigImg.setVisibility(View.VISIBLE);

        container = (ContainerLayout) findViewById(R.id.container);
        txToday = (TextView) findViewById(R.id.tx_today);
        vpCalender = (ViewPager) findViewById(R.id.vp_calender);
        viewContent = (ExpandableListView) findViewById(R.id.view_content);
        viewContent.setGroupIndicator(null);
        viewContent.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent;
                if (groupPosition == 1) {
                    intent = new Intent(MyScheduleNewActivity.this, ScheduleDetailPageActivity.class);
                    intent.putExtra("id", "123");
                } else {
                    intent = new Intent(MyScheduleNewActivity.this, ImTeacherTaskDetailActivity.class);
                    intent.putExtra("id", "123");
                }
                startActivity(intent);
                return true;
            }
        });
    }

    private void initData() {

        initCalendar();
        getData();
    }

    private void getData() {

        CheckInClass s1 = new CheckInClass();
        s1.setClassName("helo");
        CheckInClass s2 = new CheckInClass();
        s2.setClassName("helo");

        List<CheckInLesson> data = new ArrayList<>();
        List<CheckInClass> l1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s2);

        CheckInLesson checkInLesson = new CheckInLesson();
        checkInLesson.setList(new ArrayList<CheckInClass>());
        data.add(checkInLesson);

        CheckInLesson t1 = new CheckInLesson();
        t1.setLessonName("日程");
        t1.setList(l1);
        data.add(t1);

        CheckInLesson t2 = new CheckInLesson();
        t2.setLessonName("任务");
        t2.setList(l1);
        data.add(t2);


        if (data.size() != 0) {
            viewContent.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(data);
            data.clear();
            adapter = new ScheduleTaskListAdapter(this, mDatas);
            adapter.setOnSelectListener(getListener());
            viewContent.setAdapter(adapter);
            viewContent.setVisibility(View.VISIBLE);
        } else {
            viewContent.setVisibility(View.GONE);
        }
        viewContent.expandGroup(1);
    }


    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        txToday.setText(OtherUtils.formatDate(calendar.getTime()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 3; i++) {
            CalendarView calendarView = new CalendarView(MyScheduleNewActivity.this, i, year, month);
            calendarView.setOnCalendarClickListener(new OnMyCalendarClickerListener());
            if (i == 0) {
                container.setRowNum(calendarView.getColorDataPosition() / 7);
            }
            calenderViews.add(calendarView);
        }
        final TopViewPagerAdapter adapter = new TopViewPagerAdapter(this, calenderViews, INIT_PAGER_INDEX, calendar);
        vpCalender.setAdapter(adapter);
        vpCalender.setCurrentItem(INIT_PAGER_INDEX);
        vpCalender.addOnPageChangeListener(new OnMyViewPageChangeListener());

        vpCalender.post(new Runnable() {
            @Override
            public void run() {
                initEventDays((CalendarView) adapter.getChildView(0));
            }
        });
    }

    /**
     * @param calendarView
     */
    private void initEventDays(CalendarView calendarView) {
        //设置含有事件的日期 1-9号
        List<String> eventDays = new ArrayList<>();//根据实际情况调整，传入时间格式(yyyy-MM)
        for (int j = 0; j < 10; j++) {
            eventDays.add(calendarView.getCurrentDay() + "-0" + j);
        }
        calendarView.setEventDays(eventDays);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img:
                finish();
                break;
            case R.id.right_img:
                startActivity(new Intent(MyScheduleNewActivity.this, CreateSchedulectivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 日期滚动时回调
     */
    class OnMyViewPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            CalendarView calendarView = (CalendarView) calenderViews.get(position % 3);
            txToday.setText(calendarView.getCurrentDay());

            //此处添加刷新事件
            refresh();

            container.setRowNum(0);
            calendarView.initFirstDayPosition(0);

            //设置含有事件的日期 1-9号
            initEventDays(calendarView);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void refresh() {
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击某个日期回调
     */
    class OnMyCalendarClickerListener implements CalendarView.OnCalendarClickListener {
        @Override
        public void onCalendarClick(int position, DateBean dateBean) {
            txToday.setText(OtherUtils.formatDate(dateBean.getDate()));
            refresh();
        }
    }

    private ScheduleTaskListAdapter.OnSelectListener getListener() {
        return new ScheduleTaskListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int index) {
                if (index == expandIndex && viewContent.isGroupExpanded(index)) {
                    viewContent.collapseGroup(index);
                    expandIndex = -1;
                    return;
                }
                if (expandIndex != index && expandIndex != -1)
                    viewContent.collapseGroup(expandIndex);
                viewContent.expandGroup(index);
                expandIndex = index;
            }
        };
    }
}
