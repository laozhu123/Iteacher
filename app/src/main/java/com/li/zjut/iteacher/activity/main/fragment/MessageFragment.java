package com.li.zjut.iteacher.activity.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.activity.message.MessageDetailActivity;
import com.li.zjut.iteacher.adapter.message.MessageListAdapter;
import com.li.zjut.iteacher.bean.message.MessageContent;
import com.li.zjut.iteacher.widget.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class MessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    protected ListView mListView;
    private List<MessageContent> mList = new ArrayList<>();
    MessageListAdapter adapter;
    SwipeRefreshLayout mSwipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        MainActivity.content.setVisibility(View.VISIBLE);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getString(R.string.message));

        adapter = new MessageListAdapter(mList, getActivity());
        initView(view);
        getDateFromNetwork();
        return view;
    }

    private void getDateFromNetwork() {

        mList.add(new MessageContent("hleofie", "url", "helo", 1, "2016/8/25 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 2, "2016/8/16 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 3, "2016/8/16 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 2, "2016/8/24 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 1, "2016/8/16 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 2, "2016/8/23 20:18"));
        mList.add(new MessageContent("hleofie", "url", "helo", 3, "2016/8/16 20:18"));
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
                startActivity(new Intent(getActivity(), MessageDetailActivity.class));
            }
        });
    }

    @Override
    public void onLoad() {
        mList.add(new MessageContent("hleofie", "url", "helo", 1, "2016/8/16 20:18"));
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
        mList.add(new MessageContent("hleofie", "url", "helo", 1, "2016/8/16 20:18"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
