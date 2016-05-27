package com.li.zjut.iteacher.activity.myLesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.bean.mylesson.Curriculum;
import com.li.zjut.iteacher.bean.mylesson.LessonBean;
import com.li.zjut.iteacher.widget.curimlum.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class ModLesson1Activity extends BaseActivity {

    private LinearLayout mLinearLayout;
    private LayoutInflater mInflater;
    private int mIndex = 1;
    private List<View> mTimeView = new ArrayList<>();
    private TextView mLastWeekView, mLastSingleView, mTime, mWeekday;
    private View mView;
    private LessonBean lessonbean = new LessonBean();
    private String mCourseTimeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlesson);
        super.setContext(findViewById(R.id.toolbar),getString(R.string.changeLesson),true);

        mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {

        mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        findViewById(R.id.add).setOnClickListener(listener);

        ImageView rig_img = (ImageView) findViewById(R.id.right_img);
        rig_img.setImageResource(R.drawable.go);
        rig_img.setVisibility(View.VISIBLE);
        rig_img.setOnClickListener(listener);

        EditText coursename = (EditText) findViewById(R.id.coursename);
        List<Curriculum> ls = (List<Curriculum>) getIntent().getSerializableExtra("Curriculum");
        coursename.setText(ls.get(0).getText());
        mCourseTimeId = ls.get(0).getCourseTimeId();
        for (Curriculum curriculum : ls) {
            addView(curriculum);
        }
    }

    private void addView(Curriculum curriculum) {

        View layout = mInflater.inflate(R.layout.lesson_new_time, null);
        mTimeView.add(layout);
        TextView timeTitle = (TextView) layout.findViewById(R.id.time_title);
        timeTitle.setText("上课时间" + mTimeView.size());
        layout.findViewById(R.id.week_layout).setOnClickListener(listener);
        layout.findViewById(R.id.time_layout).setOnClickListener(listener);
        View delete = layout.findViewById(R.id.delete);
        ((TextView) layout.findViewById(R.id.time)).setText(curriculum.getBegin() + "-" + curriculum.getEnd());
        ((TextView) layout.findViewById(R.id.weekday)).setText(getResources().getStringArray(R.array.weekdays)[curriculum.getWeekday() - 1]);
        ((TextView) layout.findViewById(R.id.week)).setText(curriculum.getFromWeek() + "-" + curriculum.getEndWeek());
        ((TextView) layout.findViewById(R.id.singledouble)).setText(getResources().getStringArray(R.array.singledouble)[curriculum.getSingle_double() - 1]);
        ((TextView) layout.findViewById(R.id.place)).setText(curriculum.getPlace());
        delete.setTag(mIndex);
        layout.setTag(mIndex++);
        delete.setOnClickListener(listener);
        mLinearLayout.addView(layout);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView = v;
            switch (v.getId()) {
                case R.id.add:
                    addView();
                    break;
                case R.id.delete:
                    remove(v.getTag().toString());
                    break;
                case R.id.week_layout:
                    mLastWeekView = (TextView) v.findViewById(R.id.week);
                    mLastSingleView = (TextView) v.findViewById(R.id.singledouble);
                    showDialogSetWeek();
                    break;
                case R.id.left_img:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                case R.id.right_img:
                    if (!getBackData())
                        break;
                    modCourse();   //到服务器修改课程
                    break;
                case R.id.time_layout:
                    mTime = (TextView) v.findViewById(R.id.time);
                    mWeekday = (TextView) v.findViewById(R.id.weekday);
                    showDialogSetTime();
                    break;
            }
        }
    };

    private void modCourse() {

        for (Curriculum c : lessonbean.getTimes()) {
            c.setCourseTimeId(mCourseTimeId);
        }
        setResult(RESULT_OK, new Intent().putExtra("Curriculum", lessonbean));
        finish();
    }

    private boolean getBackData() {
        EditText coursename = (EditText) findViewById(R.id.coursename);
        if (coursename.getText().toString().equals("")) {
            Toast.makeText(this, "请输入课程名", Toast.LENGTH_SHORT).show();
            return false;
        }
        lessonbean.setCoursename(coursename.getText().toString());
        lessonbean.setTimes(new ArrayList<Curriculum>());
        for (View v : mTimeView) {
            Log.d("ne", "helo");
            Curriculum time = new Curriculum();

            TextView week = (TextView) v.findViewById(R.id.week);
            String[] weeks = week.getText().toString().split("-");
            time.setText(lessonbean.getCoursename());
            time.setFromWeek(Integer.parseInt(weeks[0]));
            time.setEndWeek(Integer.parseInt(weeks[1]));
            TextView single = (TextView) v.findViewById(R.id.singledouble);
            String singlestring = single.getText().toString();
            int singleIndex = 0;
            for (String s : getResources().getStringArray(R.array.singledouble)) {
                if (singlestring.equals(s)) {
                    break;
                }
                singleIndex++;
            }
            time.setSingle_double(singleIndex + 1);

            TextView time1 = (TextView) v.findViewById(R.id.time);
            String[] times = time1.getText().toString().split("-");
            time.setBegin(Integer.parseInt(times[0]));
            time.setEnd(Integer.parseInt(times[1]));
            TextView weekday = (TextView) v.findViewById(R.id.weekday);
            String weekdaystring = weekday.getText().toString();
            int weekdayIndex = 0;
            for (String s : getResources().getStringArray(R.array.weekdays)) {
                if (weekdaystring.equals(s)) {
                    break;
                }
                weekdayIndex++;
            }
            time.setWeekday(weekdayIndex + 1);

            TextView place = (TextView) v.findViewById(R.id.place);
            if (place.getText().toString().equals("")) {
                Toast.makeText(this, "请输入上课地点", Toast.LENGTH_SHORT).show();
                return false;
            }
            time.setPlace(place.getText().toString());
            lessonbean.getTimes().add(time);
        }
        return true;
    }

    private void showDialogSetTime() {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //设置你的操作事项
//            }
//        });

        builder.setPositiveButton("确定", new CustomDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, String time, String weekday) {

                dialog.dismiss();
                mTime.setText(time);
                mWeekday.setText(weekday);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        TextView time = (TextView) mView.findViewById(R.id.time);
        String[] times = time.getText().toString().split("-");
        TextView weekday = (TextView) mView.findViewById(R.id.weekday);
        String[] ss = getResources().getStringArray(R.array.weekdays);
        int sindex = 0;
        for (String s : ss) {
            if (s.equals(weekday.getText().toString())) {
                break;
            }
            sindex++;
        }

        builder.create(R.array.weekdays, CustomDialog.Builder.TIMESHOW, Integer.parseInt(times[0]), Integer.parseInt(times[1]), sindex + 1).show();
    }

    private void showDialogSetWeek() {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //设置你的操作事项
//            }
//        });

        builder.setPositiveButton("确定", new CustomDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, String fromToWeek, String weekday) {

                dialog.dismiss();
                mLastWeekView.setText(fromToWeek);
                mLastSingleView.setText(weekday);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        TextView week = (TextView) mView.findViewById(R.id.week);
        String[] weeks = week.getText().toString().split("-");
        TextView single = (TextView) mView.findViewById(R.id.singledouble);
        String[] ss = getResources().getStringArray(R.array.singledouble);
        int sindex = 0;
        for (String s : ss) {
            if (s.equals(single.getText().toString())) {
                break;
            }
            sindex++;
        }

        builder.create(R.array.singledouble, CustomDialog.Builder.WEEKSHOW, Integer.parseInt(weeks[0]), Integer.parseInt(weeks[1]), sindex + 1).show();
    }

    private void remove(String s) {

        for (View v : mTimeView) {
            if (v.getTag().toString().equals(s)) {

                mLinearLayout.removeView(v);
                mTimeView.remove(v);
                break;
            }
        }
        int index = 1;
        for (View v : mTimeView) {

            ((TextView) v.findViewById(R.id.time_title)).setText("上课时间" + index++);
        }
    }

    /*
    * 添加上课时间的控件
    * */
    private void addView() {

        View layout = mInflater.inflate(R.layout.lesson_new_time, null);
        mTimeView.add(layout);
        TextView timeTitle = (TextView) layout.findViewById(R.id.time_title);
        timeTitle.setText("上课时间" + mTimeView.size());
        layout.findViewById(R.id.week_layout).setOnClickListener(listener);
        layout.findViewById(R.id.time_layout).setOnClickListener(listener);
        View delete = layout.findViewById(R.id.delete);
        ((TextView) layout.findViewById(R.id.time)).setText(getIntent().getIntExtra("begin", 1) + "-" + getIntent().getIntExtra("begin", 1));
        ((TextView) layout.findViewById(R.id.weekday)).setText(getResources().getStringArray(R.array.weekdays)[getIntent().getIntExtra("weekday", 1) - 1]);
        delete.setTag(mIndex);
        layout.setTag(mIndex++);
        delete.setOnClickListener(listener);
        mLinearLayout.addView(layout);
    }


}
