package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.adapter.CheckInfoListAdapter;
import com.li.zjut.iteacher.activity.checkin.adapter.StuCheckInfoListAdapter;
import com.li.zjut.iteacher.activity.checkin.bean.StuCheckBean;
import com.li.zjut.iteacher.activity.imteacher.StudentInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class StuListNewActivity extends BaseActivity implements View.OnClickListener {

    ListView lvStu;
    List<StuCheckBean> mDatas = new ArrayList<>();
    StuCheckInfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list_new);
        super.setContext(findViewById(R.id.toolbar), getIntent().getStringExtra("type"), true);
        initView();
        initData();
    }

    private void initData() {
        StuCheckBean b1 = new StuCheckBean();
        b1.setName("老王和");
        b1.setPlace("杭州市西湖区");
        b1.setTime("09:50");
        LatLng latLng = new LatLng(30.26,120.19);
        b1.setLatLng(latLng);
        mDatas.add(b1);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        TextView titleRig = (TextView) findViewById(R.id.txTitleRig);
        titleRig.setText(getString(R.string.show_in_map));
        titleRig.setVisibility(View.VISIBLE);
        titleRig.setOnClickListener(this);

        lvStu = (ListView) findViewById(R.id.lv_stu);
        adapter = new StuCheckInfoListAdapter(mDatas, this);
        adapter.setListener(getListener());
        lvStu.setAdapter(adapter);
        lvStu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StuListNewActivity.this, StudentInfoActivity.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener getListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StuListNewActivity.this, MapOnePlaceActivity.class);
                intent.putExtra("teacher",new LatLng(30.26,120.19));
                intent.putExtra("student", mDatas.get((int) v.getTag()).getLatLng());
                startActivity(intent);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                Intent intent = new Intent();
                intent.setClass(StuListNewActivity.this,MapShowEveryOneActivity.class);
                intent.putExtra("id",123);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
