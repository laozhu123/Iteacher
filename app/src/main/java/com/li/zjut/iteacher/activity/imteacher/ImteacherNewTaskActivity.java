package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.imteacher.Student;
import com.li.zjut.iteacher.widget.WordWrapView;
import com.li.zjut.iteacher.widget.common.PeopleListManager;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimeListener;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimePicker;
import com.li.zjut.iteacher.widget.personinfo.MyDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImteacherNewTaskActivity extends FragmentActivity implements View.OnClickListener {

    EditText taskName, taskContent;
    TextView beginTime, endTime;
    WordWrapView wwTags, wwReceiver;
    private PeopleListManager manager;
    private SimpleDateFormat mFormatterday = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormattertime = new SimpleDateFormat("a hh:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    TextView currentView;
    ArrayList<String> ids = new ArrayList<>();

    List<String> tags = new ArrayList<>();

    MyDialog deleteDialog;
    final String TAG = "ImteacherNewTaskActivi";
    final int REQUEST_ADD_PEOPLE = 1, REQUEST_ADD_TAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imteacher_new_task);

        initView();
        initData();
    }


    private void initView() {
        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setOnClickListener(this);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(R.drawable.backarrow);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.teacher_task_create));

        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.add_tag).setOnClickListener(this);
        TextView create = (TextView) findViewById(R.id.create);
        create.setOnClickListener(this);
        create.setText(getString(R.string.teacher_task_create_btn));

        taskName = (EditText) findViewById(R.id.task_name);
        taskContent = (EditText) findViewById(R.id.task_content);
        beginTime = (TextView) findViewById(R.id.begin_time);
        endTime = (TextView) findViewById(R.id.end_time);
        wwReceiver = (WordWrapView) findViewById(R.id.receiver);
        wwTags = (WordWrapView) findViewById(R.id.tags);
        manager = new PeopleListManager(this);
        manager.setContentView(wwReceiver);

        beginTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
    }

    private void initData() {

        manager.setmListener(getClickListener());  //向容器中添加点击的监听
        manager.setmLongListener(getLongClickListener());
    }

    private String[] getTags(){
        int count = wwTags.getChildCount();
        String[] tags = new String[count];
        for (int i = 0;i < count;i++){
            tags[i] = ((TextView)wwTags.getChildAt(i)).getText().toString();
        }
        return tags;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.add:
                intent.setClass(ImteacherNewTaskActivity.this, ImteacherStuSelectActivity.class);
                intent.putStringArrayListExtra("ids", ids);
                startActivityForResult(intent, REQUEST_ADD_PEOPLE);
                break;
            case R.id.add_tag:
                intent.setClass(ImteacherNewTaskActivity.this, AddTagsActivity.class);
                intent.putExtra("tags", getTags());
                startActivityForResult(intent, REQUEST_ADD_TAG);
                break;
            case R.id.create:
                finish();
                break;
            case R.id.left_img:
                finish();
                break;
            case R.id.begin_time:
                currentView = (TextView) findViewById(R.id.begin_time);
                showTimeChoose();
                break;
            case R.id.end_time:
                currentView = (TextView) findViewById(R.id.end_time);
                showTimeChoose();
                break;
        }
    }

    private void showTimeChoose() {
        Date date = new Date();
        if (!currentView.getText().toString().equals("请选择(必填)")) {
            try {
                date = sdf.parse(currentView.getText().toString());
            } catch (ParseException e) {
                //将tv中的时间提取出来
                e.printStackTrace();
                date = new Date();
            }
        }
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {

                    @Override
                    public void onDateTimeSet(Date date) {
                        String format = mFormattertime.format(date);
                        String time = "";
                        time += mFormatterday.format(date) + " ";
                        if ((format.substring(0, 2).equals("下午") || format.substring(0, 2).equals("PM"))
                                && Integer.parseInt(format.substring(3, 5)) < 12) {
                            time += (Integer.parseInt(format
                                    .substring(3, 5)) + 12)
                                    + format.substring(5);
                        } else {
                            time += format.substring(3);
                        }
                        currentView.setText(time);
                    }
                }).setInitialDate(date).setSelectItem(1).setIs24HourTime(false)
                .setisClientSpecified24HourTime(false).build().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_ADD_PEOPLE) {  //添加任务执行人员的返回
                Log.d(TAG, "onActivityResult" + "--result_ok");
                manager.clear();
                ids.clear();
                String[] teamate = data.getStringArrayExtra("teamate");
                String[] ids = data.getStringArrayExtra("ids");
                String[] mobile = data.getStringArrayExtra("mobile");

                for (int i = 0; i < ids.length; i++) {
                    this.ids.add(ids[i]);
                    manager.addView(getStuView(teamate[i], ids[i]));
                }
            } else {
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private View getStuView(String s, String userid) {
        TextView textview = new TextView(this);
        textview.setText(s);
        textview.setBackgroundResource(R.drawable.tag_bg);
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.tag_text_color);
        textview.setTextColor(csl);
        textview.setSelected(true);
        textview.setTag(userid);
        return textview;
    }

    ;

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

    public View.OnClickListener getClickListener() {
        View.OnClickListener c = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ImteacherNewTaskActivity.this, "" + ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        };
        return c;
    }

    public View.OnLongClickListener getLongClickListener() {
        View.OnLongClickListener l = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                deleteDialog = MyDialog
                        .createMyDialog(ImteacherNewTaskActivity.this)
                        .setText("是否删除该选项？")
                        .setClickCancel(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();

                            }
                        }).setClickSure(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                                if (view.getTag() != null) {
                                    String s = view.getTag().toString();
                                    for (int i = 0; i < ids.size(); i++) {
                                        if (ids.get(i).equals(s)) {
                                            ids.remove(i);
                                            break;
                                        }
                                    }
                                }
                                wwReceiver.removeView(view);
                            }
                        });
                deleteDialog.show();
                return false;
            }
        };
        return l;
    }

}
