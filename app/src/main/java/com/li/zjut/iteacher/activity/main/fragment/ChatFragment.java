package com.li.zjut.iteacher.activity.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.li.zjut.iteacher.R;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class ChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

//        initView(view);
        return view;
    }

//    private void initView(View view) {
//
//
//        //开始登录
//        String userid = "testpro1";
//        String password = "taobao1234";
//        //此对象获取到后，保存为全局对象，供APP使用
//        //此对象跟用户相关，如果切换了用户，需要重新获取
//        final YWIMKit mIMKit = YWAPI.getIMKitInstance(userid, "23015524");
//        IYWLoginService loginService = mIMKit.getLoginService();
//        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
//        loginService.login(loginParam, new IWxCallback() {
//
//            @Override
//            public void onSuccess(Object... arg0) {
//
//                Intent intent = mIMKit.getConversationActivityIntent();
//                startActivity(intent);
//            }
//
//            @Override
//            public void onProgress(int arg0) {
//            }
//
//            @Override
//            public void onError(int errCode, String description) {
//                //如果登录失败，errCode为错误码,description是错误的具体描述信息
//            }
//        });
//    }
}
