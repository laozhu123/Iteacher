package com.li.zjut.iteacher.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.fragment.AddressFragment;
import com.li.zjut.iteacher.activity.main.fragment.ChatFragment;
import com.li.zjut.iteacher.activity.main.fragment.HomeFragment;
import com.li.zjut.iteacher.activity.main.fragment.MessageFragment;
import com.li.zjut.iteacher.bean.Tab;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.sha1.Utilgetsha1;

import java.util.ArrayList;


/*
* 并非什么主activity，只是测试FragmentTabHost和toolbar
* */
public class MainActivity extends AppCompatActivity {

    private LayoutInflater mInflater;
    private String mUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();
        initData();
    }

    private void initData() {

        mUserid = getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.USERID, "");  //获取用户id 用于获取用户数据
    }


    private void initTab() {
        ArrayList<Tab> mTabs;
        mTabs = new ArrayList<>();

        Tab home = new Tab(R.string.firstpage, R.drawable.tab_home_btn, HomeFragment.class);
        Tab message = new Tab(R.string.addressbook, R.drawable.tab_address_btn, AddressFragment.class);
        Tab friends = new Tab(R.string.message, R.drawable.tab_message_btn, MessageFragment.class);
        Tab square = new Tab(R.string.chat, R.drawable.tab_chat_btn, ChatFragment.class);
        mTabs.add(home);
        mTabs.add(message);
        mTabs.add(friends);
        mTabs.add(square);

        FragmentTabHost mTabhost;
        mInflater = LayoutInflater.from(this);
        mTabhost = (FragmentTabHost) this.findViewById(R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);        //讲realtabcontent与mTabhost相关联，realtabcontent就是展示fragment的控件

        for (Tab tab : mTabs) {
            mTabhost.addTab(mTabhost.newTabSpec(getString(tab.getTitle())).setIndicator(initIndicate(tab)),
                    tab.getFragment(), null);           //將fragment于底部的按鈕關聯起來后添加到mTabhost上，給個底部按鈕設置相應的icon
            mTabhost.getTabWidget().getChildAt(mTabhost.getTabWidget().getChildCount() - 1)
                    .setBackgroundResource(R.drawable.selector_tab_background);   //給個底部按鈕設置相應的背景圖片
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);   //去除底部按钮间的divider
        mTabhost.setCurrentTab(0);                                             //设置当前所选择的fragment为第一个
    }

    private View initIndicate(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.imageview);
        TextView text = (TextView) view.findViewById(R.id.textview);
        text.setText(tab.getTitle());
        img.setImageResource(tab.getIcon());
        return view;
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}
