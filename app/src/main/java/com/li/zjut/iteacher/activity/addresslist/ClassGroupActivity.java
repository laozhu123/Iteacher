package com.li.zjut.iteacher.activity.addresslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.adapter.address.ClassGroupListtAdapter;
import com.li.zjut.iteacher.bean.address.ClassGroup;

import java.util.ArrayList;
import java.util.List;

public class ClassGroupActivity extends BaseActivity {

    private ClassGroupListtAdapter adapter;
    private ListView lvClassGroups;
    private List<ClassGroup> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_group);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.class_group), true);

        adapter = new ClassGroupListtAdapter(mDatas, this);
        initView();
        getDataFromNetwork();
    }

    private void initView() {
        lvClassGroups = (ListView) findViewById(R.id.lv_class_groups);
        lvClassGroups.setAdapter(adapter);
        lvClassGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getDataFromNetwork() {
        ClassGroup m1 = new ClassGroup();
        m1.setClassGroupName("老朱队");
        m1.setClassGroupTeammateNum(10);
        ClassGroup m2 = new ClassGroup();
        m2.setClassGroupName("李队长的队");
        m2.setClassGroupTeammateNum(50);
        mDatas.add(m1);
        mDatas.add(m2);
        adapter.notifyDataSetChanged();
    }
}
