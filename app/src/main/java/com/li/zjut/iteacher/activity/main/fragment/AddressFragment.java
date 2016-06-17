package com.li.zjut.iteacher.activity.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.addresslist.AddressActivity;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.adapter.address.AddressListAdapter;
import com.li.zjut.iteacher.bean.address.Department;
import com.li.zjut.iteacher.bean.address.People;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class AddressFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView title;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swiperef;
    private List<Department> mDatas = new ArrayList<>();
    private AddressListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, null);
        MainActivity.content.setVisibility(View.VISIBLE);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {

        View layout = view.findViewById(R.id.toolbar);
        title = (TextView) layout.findViewById(R.id.title);
        title.setText(getString(R.string.addresslist));

        recyclerView = (RecyclerView) view.findViewById(R.id.reCycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AddressListAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AddressListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(getActivity(), AddressActivity.class).putExtra("peoples",mDatas.get(position)));
            }
        });

        swiperef = (SwipeRefreshLayout) view.findViewById(R.id.swipeRef);
        swiperef.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

        getData();

    }

    private void getData() {
        //获取通讯录从sever上
        mDatas.clear();
        if (swiperef.isRefreshing())
            swiperef.setRefreshing(false);

        for (int i = 1;i<5;i++){
            Department d1 = new Department();
            d1.setName("部门"+i);
            List<People> l = new ArrayList<>();
            for(int j = 1;j < 5;j++){
                People p = new People();
                p.setName("p"+i+j);
                p.setPhone("123335345");
                p.setUserid("sdfe");
                l.add(p);
            }
            d1.setList(l);
            mDatas.add(d1);
        }
        Department local = new Department();
        local.setName("手机通讯录");
        mDatas.add(local);
        adapter.notifyDataSetChanged();

    }
}
