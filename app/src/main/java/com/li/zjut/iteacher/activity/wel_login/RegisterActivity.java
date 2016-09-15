package com.li.zjut.iteacher.activity.wel_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.app.MyApplication;
import com.li.zjut.iteacher.bean.Ret_Register;
import com.li.zjut.iteacher.bean.register.College;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.observer.ObserverImpl;
import com.li.zjut.iteacher.http.RetrofitHttp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.SystemClock.sleep;


/**
 * A login screen that offers login via phone/password.
 */
public class RegisterActivity extends BaseActivity implements ObserverImpl {


    // UI references.
    private AutoCompleteTextView mPhoneView;
    private College college;

    private Button mBtn;
    private Handler myHandler;
    private final int changeBtn = 1;

    Thread thread;

    String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.register), true);


        college = (College) getIntent().getSerializableExtra("college");


        initView();

        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case changeBtn:
                        if (msg.arg1 == 0) {
                            mBtn.setText(getString(R.string.get_token));
                            mBtn.setOnClickListener(listener);
                        } else
                            mBtn.setText(msg.arg1 + getString(R.string.time_leave));
                        break;
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.subject_sms.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.subject_sms.detach(this);
//        SMSSDK.unregisterEventHandler(mEh);  //解除短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
                * init the ui
                * */
    private void initView() {

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);

        mBtn = (Button) findViewById(R.id.get_token);
        mBtn.setOnClickListener(listener);
        findViewById(R.id.phone_sign_in_button).setOnClickListener(listener);
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_img:
                    finish();
                    break;
                case R.id.get_token:

                    getToken();
                    break;
                case R.id.phone_sign_in_button:
                    Log.d("helo", "attempt");
                    attemptRegister();
                    break;
            }
        }
    };

    private void getToken() {

        phoneVer();
    }


    /*
    * 手机号是否正确验证
    * */
    private void phoneVer() {

        mPhoneView.setError(null);
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid phone.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Log.d(TAG, "getverificationcode");
            SMSSDK.getVerificationCode(StaticData.country, phone);
            mBtn.setOnClickListener(null);
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid phone, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        Log.d(TAG, "attemptRegister");

        // Reset errors.
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        EditText token = (EditText) findViewById(R.id.token);
        // Check for a valid phone.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        } else if (TextUtils.isEmpty(token.getText().toString())) {
            token.setError("请输入验证码");
            focusView = token;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (TextUtils.isEmpty(token.getText().toString())) {
                CommonTestUtil.toast(this, getString(R.string.please_input_token));
                return;
            } else {

                Log.d(TAG, "submitVerificationCode");
                SMSSDK.submitVerificationCode(StaticData.country, phone, token.getText().toString());
            }

        }
    }


    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic


        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);

        return m.matches();
    }


    /*
    * connect the network, and register the username and password
    * use async method
    * */
    private void register(final String phone) {

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("schoolid", college.getSchoolid());
        map.put("campusid", college.getCampusid());
        map.put("collegeid", college.getId());
        map.put("cid", getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.DEVICE_TOKEN, ""));
        map.put("device", 1);
        Call<Ret_Register> call = RetrofitHttp.github.accountRegister(map);

        call.enqueue(new Callback<Ret_Register>() {
            @Override
            public void onResponse(Call<Ret_Register> call, Response<Ret_Register> response) {


                if (response.body() != null) {
                    if (response.body().getRt() == 1) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.phone_hasbeen_register), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    SharedPreferences sps = getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE);
                    sps.edit().putBoolean(SharePerfrence.UNLOGIN, false)
                            .putString(SharePerfrence.PHONE, phone)
                            .putString(SharePerfrence.SID, response.body().getSid())
                            .putString(SharePerfrence.IM_TOKEN, response.body().getImToken())
                            .apply();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.return_null), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ret_Register> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, getString(R.string.disconnect), Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int s) {
        System.out.println("====" + s);

        switch (s) {
            case 0:
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 60; i >= 0; i--) {
                            Message msg = new Message();
                            msg.what = changeBtn;
                            msg.arg1 = i;
                            if (myHandler != null)
                                myHandler.sendMessage(msg);
                            sleep(1000);
                        }
                    }
                });
                thread.start();
                break;
            case 1:

                register(mPhoneView.getText().toString());
                break;
            case 2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, getString(R.string.fail_getver), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 4:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, getString(R.string.fail_ver), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}

