package com.li.zjut.iteacher.activity.myLesson;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.bean.mylesson.Curriculum;
import com.li.zjut.iteacher.bean.mylesson.LessonBean;
import com.li.zjut.iteacher.common.db.DBManager;
import com.li.zjut.iteacher.common.mylesson.RandomUtil;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;
import com.li.zjut.iteacher.widget.curimlum.Curriculum_table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurTableActivity extends BaseActivity {

    Curriculum_table curriculum_table;
    List<Curriculum> ls = new ArrayList<>();

    Handler handler;
    private static Context context = null;
    private final int REQUEST_ADD_CODE = 1, REQUEST_MOD_CODE = 2;
    private String mCourseTimeId;
    private boolean first = true;   //通过oncreate之后的

    private PopupWindow pop = null;
    private TextView popTxtLN, popTxtWeek, popTxtTP;
    private Button popBtnMod, popBtnDel, popBtnDelOne, popBtnInvite;
    private String[] weekdays;
    private View mHidden;

    public enum Type {DELALL, DELONE, INVITE}
    SpannableString msp = null;
    DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_table);
        super.setContext(findViewById(R.id.toolbar),getString(R.string.week),true);

        context = getApplicationContext();
        weekdays = getResources().getStringArray(R.array.weekdays);
        initView();
        pop();
        init();


        handler.postDelayed(new Runnable() {        //此处需用延迟，不然curriculum_table中的focus（）方法不能在requestData（）之前调用，也可是在网络获取延迟之后
            @Override
            public void run() {
                requestData();
            }
        }, 200);
//        requestData();
    }

    private void pop() {
        View cont = LayoutInflater.from(context)
                .inflate(R.layout.popup_lesson_detail, null);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
        pop = new PopupWindow(cont, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.popupwindow));
        popTxtLN = (TextView) cont.findViewById(R.id.lname);
        popTxtWeek = (TextView) cont.findViewById(R.id.week);
        popTxtTP = (TextView) cont.findViewById(R.id.timeplace);
        popBtnMod = (Button) cont.findViewById(R.id.btn_mod);
        popBtnDel = (Button) cont.findViewById(R.id.btn_del);
        popBtnDelOne = (Button) cont.findViewById(R.id.btn_del_one);
        popBtnInvite = (Button) cont.findViewById(R.id.invite);
    }

    private void initView() {

        mHidden = findViewById(R.id.hidden);

        ImageView right_img = (ImageView) findViewById(R.id.right_img);
        right_img.setOnClickListener(listener);
        right_img.setImageResource(R.drawable.add_title_right);
        right_img.setVisibility(View.VISIBLE);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_img:
                    finish();
                    break;
                case R.id.right_img:
                    startActivityForResult(new Intent(CurTableActivity.this, AddLesson1Activity.class), REQUEST_ADD_CODE);
                    break;
            }
        }
    };

    private void LessonDetail(final int index) {
        Curriculum lb = null;
        for (Curriculum c : ls) {
            if (c.getId() == index)
                lb = c;
        }

        popTxtLN.setText(lb != null ? lb.getText() : null);
        popTxtLN.setTextColor(0xff444444);
        String s = (lb != null ? lb.getFromWeek() : 0) + getResources().getString(R.string.split) + (lb != null ? lb.getEndWeek() : 0) + getResources().getString(R.string.weeks1)
                + weekdays[(lb != null ? lb.getWeekday() : 0) - 1];
        popTxtWeek.setText(s);
        s = (lb != null ? lb.getBegin() : 0) + getResources().getString(R.string.split) + (lb != null ? lb.getEnd() : 0)
                + getResources().getString(R.string.weeks2) + (lb != null ? lb.getPlace() : null);
        popTxtTP.setText(s);
        popBtnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modLesson(index);
                pop.dismiss();
            }
        });
        popBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delLesson(index);
            }
        });
        popBtnDelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delLessonOne(index);
            }
        });
        popBtnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInvite(index);
            }
        });
        pop.showAtLocation((View) mHidden.getParent(), Gravity.CENTER, 0, 0);
    }


    /*
    * 获取邀请码
    * */
    private void getInvite(int index) {

        for (Curriculum c : ls){
            if (c.getId() == index){
                getInviteFromHttp(c.getCourseTimeId());
                break;
            }
        }
    }

    /*
    * 连接服务器，传递coursetimeid，获取到邀请码
    * */
    private void getInviteFromHttp(String coursetimeid) {

        showCommonAlertDialog(Type.INVITE,0,"x2356d");
    }

    /*
    * 删除一节课
    * */
    private void delLessonOne(int index) {

        showCommonAlertDialog(Type.DELONE, index,"");
    }

    /*
    * 删除一门课
    * */
    private void delLesson(int index) {

        showCommonAlertDialog(Type.DELALL, index,"");
    }


    /*
    * 修改课程
    * */
    private void modLesson(int index) {

        for (Curriculum c : ls){
            if (c.getId() == index)
            {
                mCourseTimeId = c.getCourseTimeId();
                break;
            }
        }
        List<Curriculum> curls = new ArrayList<>();
        for (Curriculum curriculum : ls) {
            if (curriculum.getCourseTimeId().equals(mCourseTimeId)) {
                curls.add(curriculum);
            }
        }
        startActivityForResult(new Intent(CurTableActivity.this, ModLesson1Activity.class).putExtra("Curriculum", (Serializable) curls),
                REQUEST_MOD_CODE);
    }


    /*
    * 显示删除一门课程和一节课的dialog
    * */
    private void showCommonAlertDialog(Type s, final int index,String code) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CurTableActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle(getResources().getString(R.string.alert));
        View cont = LayoutInflater.from(context)
                .inflate(R.layout.alertdialog, null);
        if (s == Type.DELONE) {
            dialog.setMessage(getResources().getString(R.string.delone));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.sure),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            mgr.removeOneLesson(index);
                            for (int i = 0; i < ls.size(); i++) {
                                if (ls.get(i).getId()== index) {
                                    ls.remove(i);
                                    break;
                                }
                            }
                            curriculum_table.modCurriculumList(ls);
                            dialog.dismiss();
                            pop.dismiss();
                        }
                    });
        } else if (s == Type.DELALL) {
            dialog.setMessage(getResources().getString(R.string.delall));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.sure),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String curid = null;
                            for (Curriculum c : ls) {
                                if (c.getId() == index) {
                                    curid = c.getCourseTimeId();
                                    break;
                                }
                            }
                            mgr.removeOneClass(curid);
                            for (int i = 0; i < ls.size(); ) {
                                if (ls.get(i).getCourseTimeId() == curid) {
                                    ls.remove(i);
                                    continue;
                                }
                                i++;
                            }
                            curriculum_table.modCurriculumList(ls);
                            dialog.dismiss();
                            pop.dismiss();
                        }
                    });
        } else if (s == Type.INVITE) {
            dialog.setTitle(getResources().getString(R.string.invitecode));
            msp = new SpannableString(getResources().getString(R.string.invitebefore)+code+"\n"+getResources().getString(R.string.codetimelimit));
            //设置字体前景色
            msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 7, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
            dialog.setMessage(msp);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.sure),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            pop.dismiss();
                        }
                    });
        }
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        dialog.show();

    }

    private void init() {

        curriculum_table = new Curriculum_table(this);
        curriculum_table.setListener(new Curriculum_table.Cur_OnClickListener() {
            @Override
            public void OnClickListener(int weekday, int begin) {
//                startActivityForResult(new Intent(CurTableActivity.this, AddLessonActivity.class)
//                        .putExtra("weekday", weekday).putExtra("begin", begin), REQUEST_ADD_CODE);
                startActivityForResult(new Intent(CurTableActivity.this, AddLesson1Activity.class)
                        .putExtra("weekday", weekday).putExtra("begin", begin), REQUEST_ADD_CODE);
                curriculum_table.hidden();
            }
        });

        curriculum_table.setCurvListener(new Curriculum_table.Cur_v_listener() {
            @Override
            public void OnClickListener(int id) {
//                modLesson(index);
                LessonDetail(id);
            }
        });
        curriculum_table.show();

        handler = new Handler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LessonBean lessonBean;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_CODE:
//                    item = (Curriculum) data.getSerializableExtra("lessonbean");
//                    item.setIndex(ls.size());
//                    ls.add(item);
//                    curriculum_table.addCurriculum(ls);
                    lessonBean = (LessonBean) data.getSerializableExtra("Curriculum");
                    for (Curriculum curriculum : lessonBean.getTimes()) {
                        curriculum.setText(lessonBean.getCoursename());
                        //这里生成一个随机数
                        curriculum.setId(RandomUtil.getColor());
                        ls.add(curriculum);
                    }
                    mgr.add(ls);
                    curriculum_table.addCurriculumList(lessonBean.getTimes());
                    break;
                case REQUEST_MOD_CODE:
                    for (int i = 0; i < ls.size(); ) {
                        if (ls.get(i).getCourseTimeId().equals(mCourseTimeId)) {
                            ls.remove(i);
                            continue;
                        }
                        i++;
                    }
                    lessonBean = (LessonBean) data.getSerializableExtra("Curriculum");
                    for (Curriculum c : lessonBean.getTimes()){
                        //这里生成一个随机数
                        c.setId(RandomUtil.getColor());
                    }
                    ls.addAll(lessonBean.getTimes());
                    mgr.deleteOld();
                    mgr.add(ls);
                    curriculum_table.modCurriculumList(ls);
                    break;
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (first) {
            curriculum_table.focus();
            first = false;
        }
    }

    public static Context getContext() {
        return context;
    }

    private void requestData() {

        //asynctask

        mgr = new DBManager(this);
        ls = mgr.query();
        curriculum_table.addCurriculumList(ls);
    }

}


