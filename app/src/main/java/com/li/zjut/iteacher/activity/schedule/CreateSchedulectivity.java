package com.li.zjut.iteacher.activity.schedule;

import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.schedule.RepeatAdapter;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimeListener;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateSchedulectivity extends FragmentActivity implements View.OnClickListener {

    private TextView date, minute, repeat;
    private EditText content, tags;
    private SimpleDateFormat mFormatterday = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormattertime = new SimpleDateFormat("a hh:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private ListView lv;
    private PopupWindow pop = null;
    private View mHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedulectivity);

        initView();
        pop();
    }

    private void pop() {
        View cont = LayoutInflater.from(this)
                .inflate(R.layout.select_repeat, null);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
        pop = new PopupWindow(cont, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.popupwindow));
        lv = (ListView) cont.findViewById(R.id.lv);

    }

    private void initView() {

        findViewById(R.id.create).setOnClickListener(this);
        date = (TextView) findViewById(R.id.tv_date);
        date.setOnClickListener(this);
        minute = (TextView) findViewById(R.id.tv_minute);
        minute.setOnClickListener(this);
        repeat = (TextView) findViewById(R.id.repeat);
        repeat.setOnClickListener(this);
        content = (EditText) findViewById(R.id.content);
        tags = (EditText) findViewById(R.id.tags);
        mHidden = findViewById(R.id.hidden);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.create_schedule));
        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setOnClickListener(this);
        leftImg.setImageResource(R.drawable.backarrow);
        leftImg.setVisibility(View.VISIBLE);

        String date1 = getIntent().getStringExtra("date");
        if (date1 != null) {
            date.setText(date1);
        } else {
            Calendar calendar = Calendar.getInstance();
            date1 = mFormatterday.format(calendar.getTime());
            date.setText(date1);
        }
        String minute1 = getIntent().getStringExtra("minute");
        if (minute1 != null) {
            minute.setText(minute1);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.create:
                if (createJudge())
                    sumbit();
                break;
            case R.id.tv_minute:
                showTimeChoose();
                break;
            case R.id.tv_date:
                showDateChoose();
                break;
            case R.id.left_img:
                finish();
                break;
            case R.id.repeat:
                selectRepeat();
                break;
        }
    }

    private void selectRepeat() {

        String[] ss = getResources().getStringArray(R.array.repeat);
        List<String> l = new ArrayList<>();
        for (String s : ss)
            l.add(s);
        RepeatAdapter adapter = new RepeatAdapter(l,this);
        adapter.setOnItemClick(new RepeatAdapter.OnItemClick() {
            @Override
            public void onclick(int id) {
                pop.dismiss();
                repeat.setText(getResources().getStringArray(R.array.repeat)[id]);
            }
        });
        lv.setAdapter(adapter);


        pop.showAtLocation((View) mHidden.getParent(), Gravity.CENTER, 0, 0);
    }

    private boolean createJudge() {

        /*first judge data*/

        if (date.getText().toString().equals(getString(R.string.date))) {
            Toast.makeText(this, getString(R.string.please_input_date), Toast.LENGTH_SHORT).show();
        } else if (minute.getText().toString().equals(getString(R.string.minute))) {
            Toast.makeText(this, getString(R.string.please_input_minute), Toast.LENGTH_SHORT).show();
        } else if (content.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.please_input_content), Toast.LENGTH_SHORT).show();
        } else {
            Calendar c = Calendar.getInstance();
            Date d = c.getTime();
            long long1 = 0, long2 = 0;
            try {
                long1 = sdf.parse(date.getText().toString() + " " + minute.getText().toString()).getTime();
                long2 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (long1 < long2) {
                Toast.makeText(this, getString(R.string.time_pass), Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        }
        return false;
    }

    /*上传到服务器和本地数据库*/
    private void sumbit() {


    }

    private void showDateChoose() {
        Date date1 = new Date();
        String[] time = date.getText().toString().split("-");
        date1.setYear(Integer.parseInt(time[0]) - 1900);
        date1.setMonth(Integer.parseInt(time[1]) - 1);
        date1.setDate(Integer.parseInt(time[2]));
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {

                    @Override
                    public void onDateTimeSet(Date time1) {
                        date.setText(mFormatterday.format(time1));
                    }
                }).setInitialDate(date1).setSelectItem(0).setIs24HourTime(true)
                .setisClientSpecified24HourTime(true).build().show();
    }

    private void showTimeChoose() {

        Date date = new Date();
        if (!minute.getText().toString().equals(getString(R.string.minute))) {
            String[] time = minute.getText().toString().split(":");
            date.setHours(Integer.parseInt(time[0]));
            date.setMinutes(Integer.parseInt(time[1]));
        }
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {

                    @Override
                    public void onDateTimeSet(Date date) {
                        String format = mFormattertime.format(date);
                        if ((format.substring(0, 2).equals("下午") || format.substring(0, 2).equals("PM"))
                                && Integer.parseInt(format.substring(3, 5)) < 12) {
                            minute.setText((Integer.parseInt(format
                                    .substring(3, 5)) + 12)
                                    + format.substring(5));
                        } else {
                            minute.setText(format.substring(3));
                        }
                    }
                }).setInitialDate(date).setSelectItem(1).setIs24HourTime(false)
                .setisClientSpecified24HourTime(false).build().show();
    }
}
