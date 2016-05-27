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
import com.li.zjut.iteacher.adapter.register.CampusListAdapter;
import com.li.zjut.iteacher.bean.register.Campus;
import com.li.zjut.iteacher.bean.register.Ret_Allcampus;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.http.RetrofitHttp;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCampusActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TextView mTvDecr;
    private List<Campus> mDatas = new ArrayList<>();
    private CampusListAdapter mAdapter;

    String TAG = "SelectCampusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);

        super.setContext(findViewById(R.id.toolbar),getString(R.string.choose_campus),true);

        initView();
        initData();
    }


    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.cycleView);
        mTvDecr = (TextView) findViewById(R.id.noContext);

        mAdapter = new CampusListAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new CampusListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String school) {
                startActivity(new Intent(SelectCampusActivity.this, SelectCollegeActivity.class)
                        .putExtra("schoolid", getIntent().getIntExtra("schoolid", 0))
                        .putExtra("campusid", mDatas.get(position).getId()));
            }
        });
    }

    private void initData() {

        getDataFromNetwork(getIntent().getIntExtra("schoolid", 0));
    }


    /*
    * 获取所有学校的数据
    * */
    private void getDataFromNetwork(int schoolid) {

        Call<Ret_Allcampus> call = RetrofitHttp.github.getcampus(schoolid);

        call.enqueue(new Callback<Ret_Allcampus>() {

            @Override
            public void onResponse(Call<Ret_Allcampus> call, Response<Ret_Allcampus> response) {

                if (response.body().getRt() == 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mDatas.addAll(response.body().getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    mTvDecr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ret_Allcampus> call, Throwable t) {
                CommonTestUtil.toast(getApplication(), "bad");
            }
        });
    }
}
