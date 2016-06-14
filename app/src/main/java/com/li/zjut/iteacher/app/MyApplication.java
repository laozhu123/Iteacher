package com.li.zjut.iteacher.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.li.zjut.iteacher.bean.address.People;
import com.li.zjut.iteacher.common.contacts.Contacts;
import com.li.zjut.iteacher.widget.addresslist.CharacterParser;
import com.li.zjut.iteacher.widget.addresslist.GroupMemberBean;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;

/**
 * Created by LaoZhu on 2016/5/12.
 */
public class MyApplication extends Application {

    public static List<GroupMemberBean> SourceDateList;


    @Override
    public void onCreate() {
        super.onCreate();



        // 开启logcat输出，方便debug，发布时请关闭
        // XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

        // 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);


        // 其它常用的API：
        // 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
        // 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
        // 反注册（不再接收消息）：unregisterPush(context)
        // 设置标签：setTag(context, tagName)
        // 删除标签：deleteTag(context, tagName)



        //创建图片文件夹
        File destDir = new File(Environment.getExternalStorageDirectory(), "iteacher");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }


        //短信验证
        SMSSDK.initSDK(this, "132742f5b37e8", "799757b88bea7bf8d6d2843d45d9cfc8");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<People> peoples;
                peoples = Contacts.readAllContacts(getApplicationContext());
                String[] ss;
                ss = new String[peoples.size()];
                int i = 0;
                for (People people : peoples) {
                    ss[i] = people.getName();
                    i++;
                }
                SourceDateList = filledData(ss);
            }
        });
        thread.start();


//        final String APP_KEY = "23015524";
////必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
//        SysUtil.setApplication(this);
//        if(SysUtil.isTCMSServiceProcess(this)){
//            return;
//        }
////第一个参数是Application Context
////这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
//        if(SysUtil.isMainProcess()){
//            YWAPI.init(this, APP_KEY);
//        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            CharacterParser characterParser;
            characterParser = CharacterParser.getInstance();
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
