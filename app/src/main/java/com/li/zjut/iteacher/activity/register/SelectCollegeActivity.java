package com.li.zjut.iteacher.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.wel_login.RegisterActivity;
import com.li.zjut.iteacher.adapter.register.CollegeListAdapter;
import com.li.zjut.iteacher.bean.register.College;
import com.li.zjut.iteacher.bean.register.Ret_Allcollege;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.http.RetrofitHttp;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectCollegeActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TextView mTvDecr;
    private List<College> mDatas = new ArrayList<>();
    private CollegeListAdapter mAdapter;

    String TAG = "SelectCollegeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);

        super.setContext(findViewById(R.id.toolbar),getString(R.string.choose_college),true);
        initView();
        initData();
    }


    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.cycleView);
        mTvDecr = (TextView) findViewById(R.id.noContext);

        mAdapter = new CollegeListAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new CollegeListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String school) {
                mDatas.get(position).setSchoolid(getIntent().getIntExtra("schoolid", 0));
                startActivity(new Intent(SelectCollegeActivity.this, RegisterActivity.class)
                        .putExtra("college", mDatas.get(position)));
            }
        });
    }

    private void initData() {

        getDataFromNetwork(getIntent().getIntExtra("campusid", 0));
    }


    /*
    * 获取所有学校的数据
    * */
    private void getDataFromNetwork(int campusid) {

        Call<Ret_Allcollege> call = RetrofitHttp.github.getcolleges(campusid);

        call.enqueue(new Callback<Ret_Allcollege>() {

            @Override
            public void onResponse(Call<Ret_Allcollege> call, Response<Ret_Allcollege> response) {

                if (response.body().getRt() == 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mDatas.addAll(response.body().getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mTvDecr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ret_Allcollege> call, Throwable t) {
                CommonTestUtil.toast(getApplication(), "bad");
            }
        });
    }


}
