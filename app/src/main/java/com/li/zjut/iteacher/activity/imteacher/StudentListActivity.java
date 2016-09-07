package com.li.zjut.iteacher.activity.imteacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.imteacher.StudentListAdapter;
import com.li.zjut.iteacher.bean.imteacher.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends BaseActivity implements View.OnClickListener {

    private List<Student> mDatas = new ArrayList<>();
    private StudentListAdapter adapter;
    private final int REQUEST_HAND = 1, REQUEST_ADDRESS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.student_list), true);

        initView();
        initData();
    }

    /*初始化view*/
    private void initView() {
        TextView txTitleRig = (TextView) findViewById(R.id.txTitleRig);
        txTitleRig.setText("添加学生");
        txTitleRig.setOnClickListener(this);
        txTitleRig.setVisibility(View.VISIBLE);

        /*学生列表*/
        ListView lvStu = (ListView) findViewById(R.id.student_list);
        adapter = new StudentListAdapter(mDatas, this);
        lvStu.setAdapter(adapter);
        lvStu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*点击后查看学生详情*/
                startActivity(new Intent(StudentListActivity.this, StudentInfoActivity.class).putExtra("id", 156451));
            }
        });
    }

    /*初始化数据*/
    private void initData() {
        for (int i = 0; i < 5; i++) {
            Student s = new Student();
            s.setName("洪" + i);
            mDatas.add(s);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                /*添加学生的方式有两种，1是通过通讯录，2是通过手动输入电话号码*/
                showPopWindow();
                break;
        }
    }

    /*显示popwindow*/
    private void showPopWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_stu_select_popup, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                getWindow().getDecorView().getWidth() * 3 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (v.getId()) {
                    case R.id.address:
                        window.dismiss();
                        intent.setClass(StudentListActivity.this, AddStuFromAddressActivity.class);
                        startActivityForResult(intent, REQUEST_ADDRESS);
                        break;
                    case R.id.input_hand:
                        window.dismiss();
                        intent.setClass(StudentListActivity.this, AddStuByHandActivity.class);
                        startActivityForResult(intent, REQUEST_HAND);
                        break;
                    default:
                        break;
                }
            }
        };
        view.findViewById(R.id.address).setOnClickListener(listener);
        view.findViewById(R.id.input_hand).setOnClickListener(listener);
        window.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.5f);

        //添加pop窗口关闭事件
        window.setOnDismissListener(new poponDismissListener());
        window.showAtLocation(StudentListActivity.this.findViewById(R.id.toolbar),
                Gravity.CENTER_VERTICAL, 0, 0);
    }

    /*监听popwindow的dismiss事件*/
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /*设置屏幕背景透明度*/
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_HAND:
                    refresh();
                    break;
                case REQUEST_ADDRESS:
                    refresh();
                    break;
                default:
                    break;
            }
        }
    }

    /*刷新学生列表*/
    private void refresh() {

    }
}
