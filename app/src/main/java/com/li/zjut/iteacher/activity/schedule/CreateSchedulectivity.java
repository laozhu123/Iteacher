package com.li.zjut.iteacher.activity.schedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.imteacher.AddTagsActivity;
import com.li.zjut.iteacher.widget.CustomDialog;
import com.li.zjut.iteacher.widget.WordWrapView;
import com.li.zjut.iteacher.widget.common.WheelView;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimeListener;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateSchedulectivity extends BaseActivity implements View.OnClickListener {

    private TextView date, minute, repeat;
    private EditText content;
    private SimpleDateFormat mFormatterday = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormattertime = new SimpleDateFormat("a hh:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private int index = 0;
    private List<String> repeatList;                  //重复次数选择的数组

    private WordWrapView wwTags;
    ArrayList<String> tags = new ArrayList<>();      //已选标签列表
    List<View> tagViewList = new ArrayList<>();       //单个标签view的list

    final int REQUEST_ADD_TAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedulectivity);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.create_schedule), true);
        initView();
        initData();
    }

    private void initData() {
        repeatList = Arrays.asList(getResources().getStringArray(R.array.repeat));
    }


    private void initView() {
        findViewById(R.id.add_tag).setOnClickListener(this);
        wwTags = (WordWrapView) findViewById(R.id.tags);
        findViewById(R.id.create).setOnClickListener(this);
        date = (TextView) findViewById(R.id.tv_date);
        date.setOnClickListener(this);
        minute = (TextView) findViewById(R.id.tv_minute);
        minute.setOnClickListener(this);
        repeat = (TextView) findViewById(R.id.repeat);
        repeat.setOnClickListener(this);
        content = (EditText) findViewById(R.id.content);

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
        Intent intent = new Intent();
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
            case R.id.repeat:
                selectRepeat();
                break;
            case R.id.add_tag:
                intent.setClass(CreateSchedulectivity.this, AddTagsActivity.class);
                intent.putExtra("tags", getTags());
                startActivityForResult(intent, REQUEST_ADD_TAG);
                break;
            default:
                break;
        }
    }

    private String[] getTags() {
        int count = wwTags.getChildCount();
        String[] tags = new String[count];
        for (int i = 0; i < count; i++) {
            tags[i] = ((TextView) wwTags.getChildAt(i)).getText().toString();
        }
        return tags;
    }


    private void selectRepeat() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(repeatList);
        wv.setSeletion(index);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                index = selectedIndex - 2;
            }
        });

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setContentView(outerView);
        builder.setTitle("重复次数");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                repeat.setText(repeatList.get(index));
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
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
        if (!minute.getText().toString().equals(getString(R.string.please_select_minute))) {
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

    /*添加标签view*/
    private void addView(String s, boolean selected) {
        TextView textview = new TextView(this);
        textview.setText(s);
        textview.setBackgroundResource(R.drawable.tag_bg);
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.tag_text_color);
        textview.setTextColor(csl);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wwTags.removeView(v);
            }
        });
        textview.setSelected(selected);
        wwTags.addView(textview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_ADD_TAG) {  //添加标签返回
                tags = data.getStringArrayListExtra("tags");
                wwTags.removeAllViews();
                if (tags != null) {
                    for (String s : tags) {
                        addView(s, true);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
