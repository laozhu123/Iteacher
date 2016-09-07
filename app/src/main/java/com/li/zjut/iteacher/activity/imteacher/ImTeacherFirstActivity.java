package com.li.zjut.iteacher.activity.imteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.imteacher.MyStuListAdapter;
import com.li.zjut.iteacher.bean.imteacher.MyStu;

import java.util.ArrayList;
import java.util.List;

public class ImTeacherFirstActivity extends BaseActivity implements View.OnClickListener {

    View hidden, create, cancel, tv_add;
    EditText phone, name;
    ListView lv_stu;

    List<MyStu> mDatas = new ArrayList<>();
    MyStuListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_teacher_first);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.teacher_stu_list), true);

        initView();
        getDataFromNetwork();
    }

    private void getDataFromNetwork() {

        mDatas.add(new MyStu("小红", "235644623", 4));
        mDatas.add(new MyStu("小zhang", "235644623", 41));
        mDatas.add(new MyStu("小红1", "235644623", 43));
        mDatas.add(new MyStu("小zhang2", "235644623", 24));
    }

    private void initView() {
        hidden = findViewById(R.id.hidden);
        create = findViewById(R.id.create);
        cancel = findViewById(R.id.cancel);
        tv_add = findViewById(R.id.tv_add);
        phone = (EditText) findViewById(R.id.phone);
        name = (EditText) findViewById(R.id.name);
        lv_stu = (ListView) findViewById(R.id.lv_stu);

        hidden.setOnClickListener(this);
        create.setOnClickListener(this);
        cancel.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        lv_stu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ImTeacherFirstActivity.this, StuInfoActivity.class));
            }
        });
        adapter = new MyStuListAdapter(mDatas, this);
        lv_stu.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                phone.setText("");
                name.setText("");
                hidden.setVisibility(View.GONE);
                break;
            case R.id.tv_add:
                hidden.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:
            case R.id.hidden:
                phone.setText("");
                name.setText("");
                hidden.setVisibility(View.GONE);
                break;
        }
    }
}
