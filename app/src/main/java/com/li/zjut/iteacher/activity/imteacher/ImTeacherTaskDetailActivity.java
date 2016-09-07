package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.adapter.imteacher.ImteacherTaskProgressListAdapter;
import com.li.zjut.iteacher.bean.imteacher.ContentData;
import com.li.zjut.iteacher.bean.imteacher.Student;
import com.li.zjut.iteacher.common.Utils;
import com.li.zjut.iteacher.widget.WordWrapView;
import com.li.zjut.iteacher.widget.common.ContentViewManager;
import com.li.zjut.iteacher.widget.common.PeopleListManager;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimeListener;
import com.li.zjut.iteacher.widget.datepicker.SlideDateTimePicker;
import com.li.zjut.iteacher.widget.personinfo.MyDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImTeacherTaskDetailActivity extends FragmentActivity implements View.OnClickListener {


    ListView lvProgress;
    ImteacherTaskProgressListAdapter adapter;
    List<String> mDatas = new ArrayList<>();

    EditText taskName, taskContent;
    TextView beginTime, endTime;
    WordWrapView wwTags, wwReceiver;
    private PeopleListManager manager;
    private SimpleDateFormat mFormatterday = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormattertime = new SimpleDateFormat("a hh:mm");
    private View add, addTag;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    TextView currentView;
    ArrayList<String> ids = new ArrayList<>();
    View normalBtn;
    TextView save;

    ArrayList<String> tags = new ArrayList<>();
    List<View> tagViewList = new ArrayList<>();
    View.OnLongClickListener longClickListener = getLongClickListener();

    MyDialog deleteDialog;
    final String TAG = "ImteacherNewTaskActivi";
    final int REQUEST_ADD_PEOPLE = 1, REQUEST_ADD_TAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_teacher_task_detail);

        initView();
        initData();
        normal();
    }

    private void normal() {
        taskName.setEnabled(false);
        taskContent.setEnabled(false);
        add.setVisibility(View.GONE);
        addTag.setVisibility(View.GONE);
        beginTime.setClickable(false);
        endTime.setClickable(false);
        normalBtn.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);
        int count = wwReceiver.getChildCount();
        for (int i = 0; i < count; i++) {
            wwReceiver.getChildAt(i).setOnLongClickListener(null);
        }
        for (View v : tagViewList)
            v.setClickable(false);
    }

    private void modify() {
        taskName.setEnabled(true);
        taskContent.setEnabled(true);
        add.setVisibility(View.VISIBLE);
        addTag.setVisibility(View.VISIBLE);
        normalBtn.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        beginTime.setClickable(true);
        endTime.setClickable(true);
        int count = wwReceiver.getChildCount();
        for (int i = 0; i < count; i++) {
            wwReceiver.getChildAt(i).setOnLongClickListener(longClickListener);
        }
        for (View v : tagViewList)
            v.setClickable(true);
    }

    private void initView() {
        normalBtn = findViewById(R.id.normal_btn);
        save = (TextView) findViewById(R.id.save);
        save.setText(getString(R.string.save));
        save.setOnClickListener(this);

        ImageView leftImg = (ImageView) findViewById(R.id.left_img);
        leftImg.setOnClickListener(this);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(R.drawable.backarrow);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.teacher_task_detail));
        add = findViewById(R.id.add);
        addTag = findViewById(R.id.add_tag);
        add.setOnClickListener(this);
        addTag.setOnClickListener(this);
        findViewById(R.id.modify).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);

        taskName = (EditText) findViewById(R.id.task_name);
        taskContent = (EditText) findViewById(R.id.task_content);
        beginTime = (TextView) findViewById(R.id.begin_time);
        endTime = (TextView) findViewById(R.id.end_time);
        wwReceiver = (WordWrapView) findViewById(R.id.receiver);
        wwTags = (WordWrapView) findViewById(R.id.tags);
        manager = new PeopleListManager(this);
        manager.setContentView(wwReceiver);
        manager.setmLongListener(longClickListener);
        beginTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        lvProgress = (ListView) findViewById(R.id.lv_progress);
    }

    private void initData() {

        manager.setmListener(getClickListener());  //向容器中添加点击的监听
//        manager.setmLongListener(getLongClickListener());
        adapter = new ImteacherTaskProgressListAdapter(mDatas, this);
        lvProgress.setAdapter(adapter);
        mDatas.add("helo");
        mDatas.add("helo");
        mDatas.add("helo");
        adapter.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(lvProgress);
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
                intent.setClass(ImTeacherTaskDetailActivity.this, ImteacherStuSelectActivity.class);
                intent.putStringArrayListExtra("ids", ids);
                startActivityForResult(intent, REQUEST_ADD_PEOPLE);
                break;
            case R.id.add_tag:
                intent.setClass(ImTeacherTaskDetailActivity.this, AddTagsActivity.class);
                intent.putExtra("tags", getTags());
                startActivityForResult(intent, REQUEST_ADD_TAG);
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
            case R.id.delete:
                delete();
                finish();
                break;
            case R.id.modify:
                modify();
                break;
            case R.id.save:
                normal();
                break;
            default:
                break;
        }
    }

    private void delete() {
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
                    if (tags != null) {
                        wwTags.removeAllViews();
                        tagViewList.clear();//先清空tagView
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
        tagViewList.add(textview);
        textview.setText(s);
        textview.setBackgroundResource(R.drawable.tag_bg);
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.tag_text_color);
        textview.setTextColor(csl);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wwTags.removeView(v);
                removeTag(((TextView) v).getText().toString());
            }
        });
        textview.setSelected(selected);
        wwTags.addView(textview);
    }

    private void removeTag(String s) {
        for (int i = 0; i < tags.size(); i++) {
            if (s.equals(tags.get(i))) {
                tags.remove(i);
                break;
            }
        }
    }

    public View.OnClickListener getClickListener() {
        View.OnClickListener c = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ImTeacherTaskDetailActivity.this, "" + ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        };
        return c;
    }

    public View.OnLongClickListener getLongClickListener() {
        View.OnLongClickListener l = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                deleteDialog = MyDialog
                        .createMyDialog(ImTeacherTaskDetailActivity.this)
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
