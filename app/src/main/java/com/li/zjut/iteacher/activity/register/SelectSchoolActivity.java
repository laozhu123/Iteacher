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
import com.li.zjut.iteacher.activity.wel_login.LoginActivity;
import com.li.zjut.iteacher.adapter.register.SchoolListAdapter;
import com.li.zjut.iteacher.bean.register.Ret_Allschool;
import com.li.zjut.iteacher.bean.register.School;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.http.RetrofitHttp;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectSchoolActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TextView mTvDecr;
    private List<School> mDatas = new ArrayList<>();
    private SchoolListAdapter mAdapter;

    String TAG = "SelectSchoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);

        super.setContext(findViewById(R.id.toolbar),getString(R.string.choose_school),true);
        initView();
        initData();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.cycleView);
        mTvDecr = (TextView) findViewById(R.id.noContext);

        mAdapter = new SchoolListAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new SchoolListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String school) {
                startActivity(new Intent(SelectSchoolActivity.this, SelectCampusActivity.class).putExtra("schoolid", mDatas.get(position).getId()));
            }
        });
    }



    private void initData() {

        getDataFromNetwork();
    }


    /*
    * 获取所有学校的数据
    * */
    private void getDataFromNetwork() {

        Call<Ret_Allschool> call = RetrofitHttp.github.getschools();

        call.enqueue(new Callback<Ret_Allschool>() {

            @Override
            public void onResponse(Call<Ret_Allschool> call, Response<Ret_Allschool> response) {

                if (response.body().getRt() == 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mDatas.addAll(response.body().getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mTvDecr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ret_Allschool> call, Throwable t) {
                CommonTestUtil.toast(getApplication(), "bad");
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


}
