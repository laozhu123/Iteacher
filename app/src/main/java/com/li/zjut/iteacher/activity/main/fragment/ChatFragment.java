package com.li.zjut.iteacher.activity.main.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.app.MyApplication;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class ChatFragment extends Fragment {

    private TextView mTitle;
    private RelativeLayout mBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversationlist, null);
        Log.d("helo","create");
        MainActivity.content.setVisibility(View.GONE);
        setActionBarTitle(view);
        enterFragment(view);
        return view;
    }


    /**
     * 加载 会话列表 ConversationListFragment
     */
    private void enterFragment(View view) {

        ConversationListFragment fragment = (ConversationListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.conversationlist);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会非话聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话聚合显示
                .build();

        fragment.setUri(uri);
    }

    /**
     * 设置 actionbar 事件
     */
    private void setActionBarTitle(View view) {

        mTitle = (TextView) view.findViewById(R.id.txt1);
        mBack = (RelativeLayout) view.findViewById(R.id.back);

        mTitle.setText("会话列表");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

}
