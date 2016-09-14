package com.li.zjut.iteacher.activity.wel_login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.activity.register.SelectSchoolActivity;
import com.li.zjut.iteacher.app.MyApplication;
import com.li.zjut.iteacher.bean.Ret_Login;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.md5.Encryption;
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
public class LoginActivity extends BaseActivity implements ObserverImpl {


    // UI references.
    private AutoCompleteTextView mPhoneView;
    private Button mBtn;
    private final int changeBtn = 1;
    Thread thread;
    String TAG = "LoginActivity";
    private Handler myHandler;
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register:
                    register();
                    break;
                case R.id.get_token:
                    getToken();
                    break;
                case R.id.txTitleRig:
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    break;
                case R.id.phone_sign_in_button:
                    varToken();
                    break;
            }
        }
    };

    private void varToken() {
        EditText token = (EditText) findViewById(R.id.token);
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(token.getText().toString())) {
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
            Log.d(TAG, "submitVerificationCode");
            SMSSDK.submitVerificationCode(StaticData.country, mPhoneView.getText().toString(), token.getText().toString());
        }
    }

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

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic


        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);

        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("login", "oncreate");
        super.setContext(findViewById(R.id.toolbar), getString(R.string.login), false);//返回键不显示

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
        MyApplication.subject_sms.attach(this);
    }

    /*
    * init the ui
    * */
    private void initView() {

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);

        mBtn = (Button) findViewById(R.id.get_token);
        mBtn.setOnClickListener(listener);

        findViewById(R.id.register).setOnClickListener(listener);
        findViewById(R.id.phone_sign_in_button).setOnClickListener(listener);

        TextView tv = (TextView) findViewById(R.id.txTitleRig);
        tv.setOnClickListener(listener);
        tv.setText("跳过");
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("login", "onresume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPhoneView.setText(intent.getStringExtra("phone"));
    }


    private void register() {

        startActivity(new Intent(this, SelectSchoolActivity.class));
//        startActivity(new Intent().setClassName("com.example.laozhu.littlecai","com.example.laozhu.littlecai.test_task.TaskActivity2"));
    }


    private boolean isphoneValid(String phone) {
        //TODO: Replace this with your own logic


        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);

        return m.matches();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    /*
    * connect the network, and register the username and password
    * use async method
    * */
    private void login(final String phone) {

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("cid", getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getString(SharePerfrence.DEVICE_TOKEN, ""));
        map.put("device", 1);
        Call<Ret_Login> call = RetrofitHttp.github.accountLogin(map);

        call.enqueue(new Callback<Ret_Login>() {
            @Override
            public void onResponse(Call<Ret_Login> call, Response<Ret_Login> response) {


                if (response.body() != null) {
                    if (response.body().getRt() != 0) {
                        Toast.makeText(LoginActivity.this, getString(R.string.error_phone_password), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    SharedPreferences sps = getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE);

                    sps.edit()
                            .putBoolean(SharePerfrence.UNLOGIN, false)
                            .putString(SharePerfrence.USERID, response.body().getUserid())
                            .putString(SharePerfrence.SID, response.body().getSid())
                            .putString(SharePerfrence.IM_TOKEN,response.body().getImToken())
                            .apply();

                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.return_null), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ret_Login> call, Throwable t) {

                Toast.makeText(LoginActivity.this, getString(R.string.disconnect), Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.getMessage());
            }
        });

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

                login(mPhoneView.getText().toString());
                break;
            case 2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, getString(R.string.fail_getver), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 4:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, getString(R.string.fail_ver), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}

