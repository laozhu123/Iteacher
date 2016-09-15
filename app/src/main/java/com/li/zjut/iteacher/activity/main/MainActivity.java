package com.li.zjut.iteacher.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.fragment.AddressNewFragment;
import com.li.zjut.iteacher.activity.main.fragment.ChatFragment;
import com.li.zjut.iteacher.activity.main.fragment.HomeFragment;
import com.li.zjut.iteacher.activity.main.fragment.MessageFragment;
import com.li.zjut.iteacher.activity.main.fragment.MyFragment;
import com.li.zjut.iteacher.app.MyApplication;
import com.li.zjut.iteacher.bean.Tab;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.Utils;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/*
* 并非什么主activity，只是测试FragmentTabHost和toolbar
* */
public class MainActivity extends AppCompatActivity {

    private LayoutInflater mInflater;
    private TextView title;
    public static View content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect(getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.IM_TOKEN,""));//连接IM
        Log.d("helo",getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.IM_TOKEN,""));
        initTab();
        initData();
        content = findViewById(R.id.realtabcontent);
    }

    private void initData() {
        title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.chat));
    }


    private void initTab() {
        ArrayList<Tab> mTabs;
        mTabs = new ArrayList<>();

        Tab home = new Tab(R.string.firstpage, R.drawable.tab_home_btn, HomeFragment.class);
        Tab address = new Tab(R.string.addressbook, R.drawable.tab_address_btn, AddressNewFragment.class);
        Tab message = new Tab(R.string.message, R.drawable.tab_message_btn, MessageFragment.class);
        Tab chat = new Tab(R.string.chat, R.drawable.tab_chat_btn, ChatFragment.class);
        Tab my = new Tab(R.string.my, R.drawable.tab_my_btn, MyFragment.class);

        /*测试数据，用于设置小红点*/
        message.setNum(7);
        chat.setNum(6);

        mTabs.add(home);
        mTabs.add(message);
        mTabs.add(address);
        mTabs.add(chat);
        mTabs.add(my);

        mInflater = LayoutInflater.from(this);
        FragmentTabHost mTabHost = (FragmentTabHost) this.findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);        //讲realtabcontent与mTabHost相关联，realtabcontent就是展示fragment的控件

        for (Tab tab : mTabs) {
            mTabHost.addTab(mTabHost.newTabSpec(getString(tab.getTitle())).setIndicator(initIndicate(tab)),
                    tab.getFragment(), null);           //將fragment于底部的按鈕關聯起來后添加到mTabHost上，給個底部按鈕設置相應的icon

            mTabHost.getTabWidget().getChildAt(mTabHost.getTabWidget().getChildCount() - 1);   //給個底部按鈕設置相應的背景圖片

        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);   //去除底部按钮间的divider
        mTabHost.setCurrentTab(0);                                             //设置当前所选择的fragment为第一个
    }

    private View initIndicate(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.imageview);
        TextView text = (TextView) view.findViewById(R.id.textview);
        text.setText(tab.getTitle());
        img.setImageResource(tab.getIcon());
        if (tab.getNum() != 0) {
            ImageView redDot = (ImageView) view.findViewById(R.id.red_dot);
            redDot.setVisibility(View.VISIBLE);
            redDot.setImageDrawable(Utils.initCounterResources(tab.getNum(), this));
        }
        return view;
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Toast.makeText(getApplicationContext(), "im登录成功", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(MainActivity.this, ConversationListActivity.class));
                    //启动会话列表界面
//                    if (RongIM.getInstance() != null)
//                        RongIM.getInstance().startConversationList(MainActivity.this);

                    //首先需要构造使用客服者的用户信息
//                    CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
//                    CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();
//
///**
// * 启动客户服聊天界面。
// *
// * @param context           应用上下文。
// * @param customerServiceId 要与之聊天的客服 Id。
// * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
// * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
// */
//                    RongIM.getInstance().startCustomerServiceChat(MainActivity.this, "KEFU146589811674941", "在线客服",csInfo);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }
}
