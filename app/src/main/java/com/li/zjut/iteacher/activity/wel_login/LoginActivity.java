package com.li.zjut.iteacher.activity.wel_login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.li.zjut.iteacher.activity.register.SelectSchoolActivity;
import com.li.zjut.iteacher.bean.Ret_Login;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.md5.Encryption;
import com.li.zjut.iteacher.http.RetrofitHttp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via phone/password.
 */
public class LoginActivity extends BaseActivity {


    // UI references.
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    String TAG = "LoginActivity";
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register:
                    register();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("login", "oncreate");
        super.setContext(findViewById(R.id.toolbar),getString(R.string.login),false);//返回键不显示

        initView();
        setAdapter();
    }

    /*
    * init the ui
    * */
    private void initView() {

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        findViewById(R.id.register).setOnClickListener(listener);
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
        mPasswordView.setText(intent.getStringExtra("password"));
    }

    /*
            * set listener or adapter on component
            * */
    private void setAdapter() {

        /*
        * sumbit button set listener
        * */
        Button mPhoneSignInButton = (Button) findViewById(R.id.phone_sign_in_button);
        mPhoneSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid phone, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        // Check for a valid phone.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isphoneValid(phone)) {
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
            showProgress(true);
            login(phone, password);
        }
    }

    private void register() {

//        startActivityForResult(new Intent(this, RegisterActivity.class), mRequestCode);
        startActivity(new Intent(this, SelectSchoolActivity.class));
    }


    private boolean isphoneValid(String phone) {
        //TODO: Replace this with your own logic


        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
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
    private void login(final String phone, final String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", Encryption.MD5(password));
        map.put("cid", "mfie");
        map.put("device", 1);
        Call<Ret_Login> call = RetrofitHttp.github.accountLogin(map);

        call.enqueue(new Callback<Ret_Login>() {
            @Override
            public void onResponse(Call<Ret_Login> call, Response<Ret_Login> response) {

                showProgress(false);

                if (response.body() != null) {
                    if (response.body().getRt() != 0) {
                        Toast.makeText(LoginActivity.this, getString(R.string.error_phone_password), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    if (getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).getBoolean(SharePerfrence.UNLOGIN, true))
                        getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE)
                                .edit().putBoolean(SharePerfrence.UNLOGIN, false)
                                .putString("userid", response.body().getUserid())
                                .putString("sid", response.body().getSid())
                                .apply();
                    else
                        getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE)
                                .edit()
                                .putString("userid", response.body().getUserid())
                                .putString("sid", response.body().getSid())
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
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}

