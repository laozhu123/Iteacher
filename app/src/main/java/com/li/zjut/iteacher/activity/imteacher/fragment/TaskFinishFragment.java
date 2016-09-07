package com.li.zjut.iteacher.activity.imteacher.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.imteacher.ImTeacherTaskDetailActivity;
import com.li.zjut.iteacher.adapter.message.MessageListAdapter;
import com.li.zjut.iteacher.bean.message.MessageContent;
import com.li.zjut.iteacher.widget.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoZhu on 2016/7/25.
 */
public class TaskFinishFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    protected ListView mListView;
    private List<MessageContent> mList = new ArrayList<>();
    MessageListAdapter adapter;
    SwipeRefreshLayout mSwipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, null);

        adapter = new MessageListAdapter(mList, getActivity());
        initView(view);
        getDateFromNetwork();
        return view;
    }

    private void getDateFromNetwork() {

        mList.add(new MessageContent("sdfeji", "going", "helo"));
        mList.add(new MessageContent("sd", "going", "helo"));
        mList.add(new MessageContent("hleofie", "url", "helo"));
        mList.add(new MessageContent("hleofie", "url", "helo"));
        mList.add(new MessageContent("hleofie", "url", "helo"));
        mList.add(new MessageContent("hleofie", "url", "helo"));
        adapter.notifyDataSetChanged();
    }

    private void initView(View view) {

        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setAdapter(adapter);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);
        mSwipeLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        mSwipeLayout.setLoadNoFull(false);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ImTeacherTaskDetailActivity.class));
            }
        });
    }

    @Override
    public void onLoad() {
        mList.add(new MessageContent("hleofie", "url", "load"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setLoading(false);
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        mList.add(0, new MessageContent("hleofie", "url", "refresh"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
