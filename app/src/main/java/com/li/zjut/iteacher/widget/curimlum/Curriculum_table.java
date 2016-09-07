package com.li.zjut.iteacher.widget.curimlum;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.mylesson.Curriculum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoZhu on 2016/5/5.
 */
public class Curriculum_table {

    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//附加View 的 根View

    private RelativeLayout back;
    private RelativeLayout tmpLayout;

    private int gridHeight, gridWidth;

    RelativeLayout.LayoutParams layoutParams;
    private Handler handler;
    private RelativeLayout layout;

    private int xx, yy;

    private List<Curriculum> mLs = new ArrayList<>();
    private int index = 0;
    private Cur_v_listener vListener;


    private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
    );

    public Curriculum_table(Context context) {

        this.context = context;
        handler = new Handler();
        initView();
    }

    private void initView() {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.helo, decorView, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 45, 0, 0);
        rootView.setLayoutParams(params);
        contentContainer = (ViewGroup) rootView.findViewById(R.id.helo);
        contentContainer.setLayoutParams(params);

        tmpLayout = (RelativeLayout) rootView.findViewById(R.id.Monday);
        back = (RelativeLayout) rootView.findViewById(R.id.back);

    }

    public void focus() {

        gridWidth = tmpLayout.getWidth();
        gridHeight = tmpLayout.getHeight() / 12;

    }



    public void addCurriculumList(List<Curriculum> ls) {

        mLs.addAll(ls);
        for (Curriculum s : ls)
            addView(s);
    }


    public void modCurriculumList(List<Curriculum> ls) {

        layout = (RelativeLayout) rootView.findViewById(R.id.Monday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Tuesday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Wednesday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Thursday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Friday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Saturday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        layout = (RelativeLayout) rootView.findViewById(R.id.Sunday);
        for (int i = 1; i <= layout.getChildCount(); i++)
            layout.removeView(layout.getChildAt(i));
        mLs = ls;
        for (Curriculum s : mLs)
            addView(s);
    }

    private void addView(Curriculum cur) {

        TextView tv;

        switch (cur.getWeekday()) {
            case 1:
                layout = (RelativeLayout) rootView.findViewById(R.id.Monday);
                break;
            case 2:
                layout = (RelativeLayout) rootView.findViewById(R.id.Tuesday);
                break;
            case 3:
                layout = (RelativeLayout) rootView.findViewById(R.id.Wednesday);
                break;
            case 4:
                layout = (RelativeLayout) rootView.findViewById(R.id.Thursday);
                break;
            case 5:
                layout = (RelativeLayout) rootView.findViewById(R.id.Friday);
                break;
            case 6:
                layout = (RelativeLayout) rootView.findViewById(R.id.Saturday);
                break;
            case 7:
                layout = (RelativeLayout) rootView.findViewById(R.id.Sunday);
                break;
        }
        tv = createTv(cur.getBegin(), cur.getEnd(), cur.getText(), cur.getPlace());

        tv.setBackground(context.getResources().getDrawable(R.drawable.class_radius1));
        if (index % 4 == 0)
//            tv.setBackgroundColor(getResources().getColor(R.color.color1));
            tv.setBackground(context.getResources().getDrawable(R.drawable.class_radius1));
        else if (index % 4 == 1)
//            tv.setBackgroundColor(getResources().getColor(R.color.color2));
            tv.setBackground(context.getResources().getDrawable(R.drawable.class_radius2));
        else if (index % 4 == 2)
            tv.setBackground(context.getResources().getDrawable(R.drawable.class_radius3));
        else
            tv.setBackground(context.getResources().getDrawable(R.drawable.class_radius4));
//            tv.setBackgroundColor(getResources().getColor(R.color.color3));
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tv.setTag(cur.getId());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vListener.OnClickListener(Integer.parseInt(v.getTag().toString()));
//                Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(tv);
        index++;
    }

    public interface Cur_v_listener {

        void OnClickListener(int index);
    }

    public void setCurvListener(Cur_v_listener vlistener) {

        vListener = vlistener;
    }

    private TextView createTv(int start, int end, String text, String place) {
        TextView tv = new TextView(context);
        tv.setPadding(5, 2, 5, 2);
        /*
         指定高度和宽度
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight * (end - start + 1));

        /*
        指定位置
         */
        tv.setY(gridHeight * (start - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(text + "\n" + place);
        return tv;
    }

    public void show() {

        onAttached(rootView);
    }

    private void onAttached(ViewGroup rootView) {

        decorView.addView(rootView);
    }
}

