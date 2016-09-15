package com.li.zjut.iteacher.activity.email;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.common.email.AlertDialogFragment;
import com.li.zjut.iteacher.common.email.AttachItem;
import com.li.zjut.iteacher.common.email.DialogResultListener;
import com.li.zjut.iteacher.common.email.EmailFormatter;
import com.li.zjut.iteacher.common.email.StatusThemeUtil;
import com.li.zjut.iteacher.common.email.Toaster;
import com.li.zjut.iteacher.common.email.UIUtil;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 写邮件界面
 * <p/>
 * Created by michael on 16/6/3.
 */
public class NewLetterActivity extends AppCompatActivity implements DialogResultListener {


    private EditText etEmailTo;
    private EditText etSubject;
    private EditText etContent;

    private RelativeLayout rlAttach;
    private LinearLayout llAttach;

    private LinearLayout llClock;
    private TextView tvClock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusThemeUtil.setMeizuStatusBarDarkIcon(this, true);
        StatusThemeUtil.setMiuiStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_new_letter);
        iniComponent();
    }

    private void iniComponent() {
        attachPaths = new ArrayList<>();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back_gray);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rlAttach = (RelativeLayout) findViewById(R.id.rlAttach);
        llAttach = (LinearLayout) findViewById(R.id.llAttach);
        etEmailTo = (EditText) findViewById(R.id.etEmailTo);
        etSubject = (EditText) findViewById(R.id.etSubject);
        etContent = (EditText) findViewById(R.id.etContent);
        llClock = (LinearLayout) findViewById(R.id.llClock);
        tvClock = (TextView) findViewById(R.id.tvClock);


        String emailTo = getIntent().getStringExtra("emailTo");
        if (emailTo != null && !emailTo.isEmpty()) {
            etEmailTo.setText(emailTo);
            etEmailTo.setSelection(emailTo.length());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_letter_actionbar, menu);
        return true;
    }

    private static final int REQUEST_DIRECTORY = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_attach:
                UIUtil.startFilePickerActivity(NewLetterActivity.this, REQUEST_DIRECTORY);
                break;
            case R.id.action_send:
                if (checkEmail()) {
//                    saveToLocalAndPending(new String[]{etEmailTo.getText().toString()}, etSubject.getText().toString(), etContent.getText().toString(), attachPaths.toArray(new String[0]));
//                    this.finish();

                    String[] email = {etEmailTo.getText().toString()}; // 需要注意，email必须以数组形式传入
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    if (attachPaths == null || attachPaths.size() == 0) {
                        intent.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
                        intent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString()); // 主题
                        intent.putExtra(Intent.EXTRA_TEXT, etContent.getText().toString()); // 正文
                        intent.setType("message/rfc822"); // 设置邮件格式
                        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
                    } else {
                        Intent returnIt = new Intent(Intent.ACTION_SEND);

                        String[] tos = email; //send to someone

                        String[] ccs = email;  //Carbon Copy to someone

                        returnIt.putExtra(Intent.EXTRA_EMAIL, tos);

                        returnIt.putExtra(Intent.EXTRA_CC, ccs);

                        returnIt.putExtra(Intent.EXTRA_TEXT, etContent.getText().toString());

                        returnIt.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());

                        for (String s : attachPaths) {
                            Uri uri = Uri.parse(s);

                            returnIt.putExtra(Intent.EXTRA_STREAM, uri);
                        }

                        returnIt.setType("audio/mp3");  //use this format no matter what your attachment type is

                        returnIt.setType("message/rfc882");

                        Intent.createChooser(returnIt, "请选择邮件类应用");

                        startActivity(returnIt);
                    }

                }
                break;
            case android.R.id.home:
                if (needConfirmDialog()) {
                    showConfirmDialog();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 是否需要一个对话框提醒用户
     */
    private boolean needConfirmDialog() {
        String emailTo = etEmailTo.getText().toString();
        String subject = etSubject.getText().toString();
        String content = etContent.getText().toString();
        if ((emailTo != null && !emailTo.isEmpty())
                || (subject != null && !subject.isEmpty())
                || (content != null && !content.isEmpty())
                || attachPaths.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 放弃编写邮件的提醒对话框
     */
    private int REQUEST_GIVE_UP_EMAIL = 0;

    private void showConfirmDialog() {
        AlertDialogFragment.Builder builder = new AlertDialogFragment.Builder(this)
                .setRequestCode(REQUEST_GIVE_UP_EMAIL)
                .setMessage("放弃编写邮件？")
                .setHasCancelOk(true)
                .setShowTitle(false)
                .setCancel(R.string.cancel)
                .setOk(R.string.ok)
                .setCancelable(false)
                .setListener(this);
        builder.create().show(this);
    }

    @Override
    public void onDialogResult(int requestCode, int resultCode, Bundle arguments) {
        if (requestCode == REQUEST_GIVE_UP_EMAIL) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DIRECTORY && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            // Do something with the URI
//                            Toaster.show("more111:"+uri);
                            addAttachPathAndRefresh(uri);
                        }
                    }
                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra(FilePickerActivity.EXTRA_PATHS);
                    if (paths != null) {
                        for (String path : paths) {
                            Uri uri = Uri.parse(path);
                            // Do something with the URI
//                            Toaster.show("more222:"+uri);
                            addAttachPathAndRefresh(uri);
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                // Do something with the URI
//                Toaster.show("one:" + uri);
                addAttachPathAndRefresh(uri);
            }
        }
    }

    private List<String> attachPaths;

    /**
     * 添加附件地址
     */
    private void addAttachPathAndRefresh(Uri uri) {
        if (uri != null) {
            attachPaths.add(uri.getPath());
            refreshAttachList();
        }
    }

    /**
     * 移除附件地址
     */
    private void removeAttachPathAndRefresh(int position) {
        if (attachPaths != null && !attachPaths.isEmpty() && attachPaths.get(position) != null) {
            attachPaths.remove(position);
        }
        refreshAttachList();
    }

    /**
     * 刷新附件UI
     */
    private void refreshAttachList() {
        llAttach.removeAllViews();
        if (attachPaths == null || attachPaths.isEmpty()) {
            rlAttach.setVisibility(View.GONE);
        } else {
            rlAttach.setVisibility(View.VISIBLE);
            int size = attachPaths.size();
            for (int i = 0; i < size; i++) {
                String attachPath = attachPaths.get(i);
                if (attachPath != null) {
                    AttachItem attachItem = new AttachItem(NewLetterActivity.this);
                    attachItem.setAttach(i, attachPath);
                    attachItem.setOnAttachDeleteListener(new AttachItem.OnAttachDeleteListener() {
                        @Override
                        public void onDelete(int position) {
                            removeAttachPathAndRefresh(position);
                        }
                    });
                    llAttach.addView(attachItem);
                    llAttach.invalidate();
                }
            }
        }
    }

    /**
     * 是否符合Email的规范
     */
    private boolean checkEmail() {
        //检查邮箱
        String email = etEmailTo.getText().toString();
        if (email == null || email.isEmpty()) {
            Toaster.show("邮箱不能为空");
            etEmailTo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
            return false;
        } else if (!EmailFormatter.isEmailFormat(email)) {
            Toaster.show("邮箱不符合规范");
            etEmailTo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
            return false;
        }

        //检查Subject
        String subject = etSubject.getText().toString();
        if (subject == null || subject.isEmpty()) {
            Toaster.show("主题不能为空");
            etSubject.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
            return false;
        }


        return true;
    }


//    private void sendEmailAsyncTask(final String[] emailTo, final String subject, final String content, final String[] attachFileNames)
//    {
//        this.emailTo = emailTo;
//        this.subject = subject;
//        this.content = content;
//        this.attachFileNames = attachFileNames;
//        new AsyncCaller().execute();
//    }

//    String[] emailTo;
//    String subject;
//    String content;
//    String[] attachFileNames;
//
//    private class AsyncCaller extends AsyncTask<Void, Void, Void>
//    {
//        ProgressDialog pdLoading = new ProgressDialog(NewLetterActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            //this method will be running on UI thread
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.show();
//        }
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            sendEmailBy163WithAttach(emailTo, subject, content, attachFileNames);
//            //this method will be running on background thread so don't update UI frome here
//            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//            L.e(TAG, "onResult:"+result);
//            Toaster.show(""+result);
//            //this method will be running on UI thread
//
//            pdLoading.dismiss();
//        }
//    }


}
