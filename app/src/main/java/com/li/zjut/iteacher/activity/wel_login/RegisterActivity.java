package com.li.zjut.iteacher.activity.wel_login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.li.zjut.iteacher.bean.Ret_Register;
import com.li.zjut.iteacher.bean.register.College;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.md5.Encryption;
import com.li.zjut.iteacher.http.RetrofitHttp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.utils.SMSLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.SystemClock.sleep;


/**
 * A login screen that offers login via phone/password.
 */
public class RegisterActivity extends BaseActivity {


    // UI references.
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordRepeatView;
    private View mProgressView;
    private View mLoginFormView;
    private int mResultCode = 1;
    private int ANDROID = 1;
    private College college;
    private EventHandler mEh;
    private Button mBtn;
    private static Handler myHandler;
    private final int changeBtn = 1;

    Thread thread;

    String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        super.setContext(findViewById(R.id.toolbar),getString(R.string.register),true);


        college = (College) getIntent().getSerializableExtra("college");


        initView();

        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case changeBtn:
                        if (msg.arg1 == 0){
                            mBtn.setText(getString(R.string.get_token));
                            mBtn.setOnClickListener(listener);
                        }
                        else
                            mBtn.setText(msg.arg1 + getString(R.string.time_leave));
                        break;
                }
            }
        };

        mEh = new EventHandler() {

            @Override
            public void beforeEvent(int event, Object data) {
                Log.d(TAG, "before");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    showProgress(true);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Log.d(TAG, "before");
                    StaticData.closeThread = true;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 60; i >= 0; i--) {
                                if (!StaticData.closeThread)
                                    return;
                                Message msg = new Message();
                                msg.what = changeBtn;
                                msg.arg1 = i;
                                myHandler.sendMessage(msg);
                                sleep(1000);
                            }
                            mBtn.setOnClickListener(listener);
                        }
                    });
                    thread.start();
                }
            }

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        register(mPhoneView.getText().toString(), mPasswordView.getText().toString());
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        findViewById(R.id.phone_sign_in_button).setOnClickListener(listener);

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.d(TAG, data.toString());
                    }
                } else {
                    if (event == 2 && data != null && data instanceof UserInterruptException) {
                        Log.d(TAG,getString(R.string.fail_getver));
                        return;
                    }
                    if (event == 3){
                        Log.d(TAG,getString(R.string.fail_ver)+"and so on");
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
                            Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception var5) {
                        SMSLog.getInstance().w(var5);
                    }

                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(mEh); //注册短信回调
    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(mEh);  //解除短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticData.closeThread = false;
    }

    /*
                * init the ui
                * */
    private void initView() {

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordRepeatView = (EditText) findViewById(R.id.password_repeat);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mBtn = (Button) findViewById(R.id.get_token);
        mBtn.setOnClickListener(listener);
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_img:
                    finish();
                    break;
                case R.id.get_token:
                    mBtn.setOnClickListener(null);
                    getToken();
                    break;
                case R.id.phone_sign_in_button:

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
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password_repeat = mPasswordRepeatView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password repeat, if the user entered one.
        if (!cancel && !password.equals(password_repeat)) {
            mPasswordRepeatView.setError(getString(R.string.error_repeat_password));
            focusView = mPasswordRepeatView;
            cancel = true;
        }

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
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            EditText token = (EditText) findViewById(R.id.token);
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

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*
    * connect the network, and register the username and password
    * use async method
    * */
    private void register(final String phone, final String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", Encryption.MD5(password));
        map.put("schoolid", college.getSchoolid());
        map.put("campusid", college.getCampusid());
        map.put("collegeid", college.getId());
        map.put("cid", StaticData.cid);

        map.put("device", ANDROID);
        Call<Ret_Register> call = RetrofitHttp.github.accountRegister(map);

        call.enqueue(new Callback<Ret_Register>() {
            @Override
            public void onResponse(Call<Ret_Register> call, Response<Ret_Register> response) {

                showProgress(false);

                if (response.body() != null) {
                    if (response.body().getRt() == 1) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.phone_hasbeen_register), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit()
                            .putString(SharePerfrence.PHONE, phone)
                            .putString(SharePerfrence.PASSWORD, password)
                            .commit();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class).putExtra("phone", phone).putExtra("password", password));
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
}

