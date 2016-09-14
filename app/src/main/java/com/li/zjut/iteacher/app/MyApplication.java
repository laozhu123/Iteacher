package com.li.zjut.iteacher.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.address.People;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.contacts.Contacts;
import com.li.zjut.iteacher.common.observer.SubjectImpl_sms;
import com.li.zjut.iteacher.widget.addresslist.CharacterParser;
import com.li.zjut.iteacher.widget.addresslist.GroupMemberBean;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.utils.SMSLog;
import io.rong.imkit.RongIM;

import static android.os.SystemClock.sleep;

/**
 * Created by LaoZhu on 2016/5/12.
 */
public class MyApplication extends Application {

    public static List<GroupMemberBean> SourceDateList;
    EventHandler mEh;
    String TAG = "app";
    public static Subject_sms subject_sms = new Subject_sms();

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }

        // 开启logcat输出，方便debug，发布时请关闭
        // XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
                getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit()
                        .putString(SharePerfrence.DEVICE_TOKEN, data.toString())
                        .commit();
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

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
        mEh = new EventHandler() {

            @Override
            public void beforeEvent(int event, Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    showProgress(true);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    subject_sms.notifyAll(0);
                }
            }

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        subject_sms.notifyAll(1);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        subject_sms.notifyAll(2);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.d(TAG, data.toString());
                    }
                } else {
                    if (event == 2 && data != null && data instanceof UserInterruptException) {
                        subject_sms.notifyAll(3);
                        return;
                    }
                    if (event == 3) {
                        subject_sms.notifyAll(4);
                        return;
                    }


                    try {
                        if (data != null) {
                            ((Throwable) data).printStackTrace();
                        }
                        Throwable resId = (Throwable) data;
                        assert resId != null;
                        JSONObject object = new JSONObject(resId.getMessage());
                        String des = object.optString("detail");
//                        int status1 = object.optInt("status");

                        if (!TextUtils.isEmpty(des)) {
                            Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception var5) {
                        SMSLog.getInstance().w(var5);
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(mEh); //注册短信回调


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<People> peoples;
                peoples = Contacts.readAllContacts(getApplicationContext());

                SourceDateList = filledData(peoples);
                if (SourceDateList.size() == 0) {
                    peoples = new ArrayList<>();
                    People p1 = new People();
                    p1.setName("helo");
                    p1.setPhone("123456568");
                    People p2 = new People();
                    p2.setName("dela");
                    p2.setPhone("123456568");
                    peoples.add(p1);
                    peoples.add(p2);

                    SourceDateList = filledData(peoples);
                }
            }
        });
        thread.start();

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(List<People> date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.size(); i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date.get(i).getName());
            sortModel.setPhone(date.get(i).getPhone());
            // 汉字转换成拼音
            CharacterParser characterParser;
            characterParser = CharacterParser.getInstance();
            String pinyin = characterParser.getSelling(date.get(i).getName());
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

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
