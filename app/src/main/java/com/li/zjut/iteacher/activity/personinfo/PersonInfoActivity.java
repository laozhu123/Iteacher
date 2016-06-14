package com.li.zjut.iteacher.activity.personinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.wel_login.LoginActivity;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.update.UpdateManager;
import com.li.zjut.iteacher.widget.personinfo.MyChooseDialog;
import com.li.zjut.iteacher.widget.personinfo.MyProDialog;
import com.li.zjut.iteacher.widget.personinfo.PasswordDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonInfoActivity extends BaseActivity {

    String fileName;
    String photoPath;


    RelativeLayout photo_btn, nickname_btn, name_btn,  sex_btn,
            birthday_btn, phone_btn, email_btn, qq_btn, diploma_btn, editpassword_btn, feedback_btn,
            workphone_btn, shortphone_btn, weixin_btn, feixin_btn, laiwang_btn, skype_btn, yixin_btn, mailplace_btn;

    TextView nickname, name,  sex, birthday, phone, email, qq, diploma,
            workphone, shortphone, weixin, yixin, feixin, laiwang, skype, mailplace, feedback;
    ImageView photo;
    MyChooseDialog sexDialog, photoDialog;
    PasswordDialog passwordDialog;

    MyProDialog proDialog;

    boolean isSex;

    String versionName;

    public static final int EDIT_INFOMATION = 0;
    public static final int CAMERA = 1;
    public static final int PICTURE = 2;
    public static final int CROP_PICTURE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.myinfo), true);

        initView();
    }

    private void initView() {

        findViewById(R.id.loginout).setOnClickListener(listener);
        photo_btn = (RelativeLayout) findView(R.id.photo_btn);
        nickname_btn = (RelativeLayout) findView(R.id.nickname_btn);
        name_btn = (RelativeLayout) findView(R.id.name_btn);
        sex_btn = (RelativeLayout) findView(R.id.sex_btn);
        birthday_btn = (RelativeLayout) findView(R.id.birthday_btn);
        phone_btn = (RelativeLayout) findView(R.id.phone_btn);
        email_btn = (RelativeLayout) findView(R.id.email_btn);
        qq_btn = (RelativeLayout) findView(R.id.qq_btn);
        weixin_btn = (RelativeLayout) findView(R.id.weixin_btn);
        yixin_btn = (RelativeLayout) findView(R.id.yixin_btn);
        feixin_btn = (RelativeLayout) findView(R.id.feixin_btn);
        laiwang_btn = (RelativeLayout) findView(R.id.laiwang_btn);
        skype_btn = (RelativeLayout) findView(R.id.skype_btn);
        workphone_btn = (RelativeLayout) findView(R.id.workphone_btn);
        shortphone_btn = (RelativeLayout) findView(R.id.shortphone_btn);
        mailplace_btn = (RelativeLayout) findView(R.id.mailplace_btn);
//		diploma_btn = (RelativeLayout)findView(R.id.diploma_btn);
        editpassword_btn = (RelativeLayout) findView(R.id.editpassword_btn);
        feedback_btn = (RelativeLayout) findView(R.id.feedback_btn);

        nickname = (TextView) findView(R.id.nickname);
        name = (TextView) findView(R.id.name);
        sex = (TextView) findView(R.id.sex);
        birthday = (TextView) findView(R.id.birthday);
        phone = (TextView) findView(R.id.phone);
        email = (TextView) findView(R.id.email);
        qq = (TextView) findView(R.id.qq);
        weixin = (TextView) findView(R.id.weixin);
        yixin = (TextView) findView(R.id.yixin);
        feixin = (TextView) findView(R.id.feixin);
        laiwang = (TextView) findView(R.id.laiwang);
        skype = (TextView) findView(R.id.skype);
        workphone = (TextView) findView(R.id.workphone);
        shortphone = (TextView) findView(R.id.shortphone);
        mailplace = (TextView) findView(R.id.mailplace);
        feedback = (TextView) findView(R.id.feedback);
//		diploma = (TextView)findView(R.id.diploma);
        photo = (ImageView) findView(R.id.photo);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (!sharedPreferences.getString("icon", "").equals("")) {
            if (sharedPreferences.getString("icon", "").substring(0, 5).equals("http:")) {
                photo.setImageResource(R.mipmap.eximg);
            } else {
                photo.setImageURI(Uri.parse(sharedPreferences.getString("icon", "")));
            }
        } else {
            photo.setImageResource(R.mipmap.eximg);
        }
        nickname.setText(sharedPreferences.getString("nickname", ""));
        name.setText(sharedPreferences.getString("name", ""));
        sex.setText(sharedPreferences.getInt("gender", 0) == 0 ? "男" : "女");
        birthday.setText(sharedPreferences.getString("birthday", ""));
        phone.setText(sharedPreferences.getString("mobile", ""));
        email.setText(sharedPreferences.getString("email", ""));
        qq.setText(sharedPreferences.getString("qq", ""));
        workphone.setText(sharedPreferences.getString("workphone", ""));
        shortphone.setText(sharedPreferences.getString("shortphone", ""));
        weixin.setText(sharedPreferences.getString("weixin", ""));
        yixin.setText(sharedPreferences.getString("yixin", ""));
        feixin.setText(sharedPreferences.getString("feixin", ""));
        skype.setText(sharedPreferences.getString("skype", ""));
        laiwang.setText(sharedPreferences.getString("laiwang", ""));
        mailplace.setText(sharedPreferences.getString("mailingaddress", ""));
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = getPackageManager().getPackageInfo(
                    "com.li.zjut.iteacher", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        feedback.setText(versionName);

        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = getPackageManager().getPackageInfo(
                    "com.li.zjut.iteacher", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//		diploma.setText(sharedPreferences.getString("education", ""));

    }

    private View findView(int id) {
        View v = findViewById(id);
        v.setOnClickListener(listener);
        return v;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginout:
                    loginout();
                    break;
                case R.id.photo_btn:
                    choosePhoto();
                    break;
                case R.id.photo:
                    Intent intent = new Intent(PersonInfoActivity.this, BigPicActivity.class);
                    intent.putExtra("url", getSharedPreferences("userInfo", MODE_PRIVATE).getString("bigicon", ""));
                    startActivity(intent);
                    break;
                case R.id.nickname_btn:
                    edit("昵称", "nickname", nickname.getText().toString());
                    break;
                case R.id.email_btn:
                    edit("邮箱", "email", email.getText().toString());
                    break;
                case R.id.qq_btn:
                    edit("QQ", "qq", qq.getText().toString());
                    break;
                case R.id.workphone_btn:
                    edit("工作电话", "workphone", workphone.getText().toString());
                    break;
                case R.id.shortphone_btn:
                    edit("短号", "shortphone", shortphone.getText().toString());
                    break;
                case R.id.weixin_btn:
                    edit("微信", "weixin", weixin.getText().toString());
                    break;
                case R.id.yixin_btn:
                    edit("易信", "yixin", yixin.getText().toString());
                    break;
                case R.id.feixin_btn:
                    edit("飞信", "feixin", feixin.getText().toString());
                    break;
                case R.id.skype_btn:
                    edit("skype", "skype", skype.getText().toString());
                    break;
                case R.id.laiwang_btn:
                    edit("来往", "laiwang", laiwang.getText().toString());
                    break;
                case R.id.mailplace_btn:
                    edit("邮寄地址", "mailplace", mailplace.getText().toString());
                    break;
                case R.id.editpassword_btn:
                    checkPassword();
                    break;
                case R.id.feedback_btn:
                    checkVersion();
                default:
                    break;
            }

        }
    };

    private void checkVersion(){
        UpdateManager updateManager = new UpdateManager(this);
        updateManager.checkUpdate(UpdateManager.ONCLICK_CHECK_UPDATE);

    }

    /**
     * 确认密码
     */
    private void checkPassword() {
        passwordDialog = passwordDialog.createPasswordDialog(this).setTitle("验证原密码").setText("为保护您的账户安全，修改密码前请填写原密码").setClickCancel(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                passwordDialog.dismiss();
            }
        }).setClickSure(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                passwordDialog.dismiss();
            }
        });
        passwordDialog.show();

    }

    /**
     * 挑选头像
     */
    private void choosePhoto() {
        fileName = getSharedPreferences("userInfo", MODE_PRIVATE).getString("accountid", "") + "headphoto.jpg";
        photoPath = new File(Environment.getExternalStorageDirectory(), "iteacher").getPath();
        photoDialog = MyChooseDialog.createMyDialog(this).setTextTitle("选择图片").setImage1(false).setImage2(false).setText1("相册").setText2("拍照").
                setClick1(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        photoDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICTURE);
                    }
                }).
                setClick2(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        photoDialog.dismiss();
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = new File(Environment.getExternalStorageDirectory(), "ZTWB");
                        String tempName = "temp.jpg";
                        if (!file.exists())
                            file.mkdirs();
                        for (File pic : file.listFiles()) {
                            if (pic.getName().equals(tempName)) {
                                pic.delete();
                            }
                        }
                        Uri uri = Uri.fromFile(new File(photoPath, tempName));
                        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(camera, CAMERA);
                    }
                });
        photoDialog.show();
    }

    /**
     * 退出登录
     */
    private void loginout() {
        getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit().putBoolean(SharePerfrence.UNLOGIN, true).apply();
        startActivity(new Intent(getApplication(), LoginActivity.class));
        finish();

    }


    private void edit(String key, String key_e, String values) {
        Intent intent = new Intent(this, EditPersonalInfoActivity.class);
        intent.putExtra("edit_key", key);
        intent.putExtra("edit_key_e", key_e);
        intent.putExtra("edit_values", values);
        startActivityForResult(intent, EDIT_INFOMATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_INFOMATION && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("key_e");
            if (result.equals("nickname")) {
                nickname.setText(data.getStringExtra("values"));
            }
            if (result.equals("email")) {
                email.setText(data.getStringExtra("values"));
            }
            if (result.equals("qq")) {
                qq.setText(data.getStringExtra("values"));
            }
            if (result.equals("workphone")) {
                workphone.setText(data.getStringExtra("values"));
            }
            if (result.equals("shortphone")) {
                shortphone.setText(data.getStringExtra("values"));
            }
            if (result.equals("weixin")) {
                weixin.setText(data.getStringExtra("values"));
            }
            if (result.equals("feixin")) {
                feixin.setText(data.getStringExtra("values"));
            }
            if (result.equals("yixin")) {
                yixin.setText(data.getStringExtra("values"));
            }
            if (result.equals("skype")) {
                skype.setText(data.getStringExtra("values"));
            }
            if (result.equals("laiwang")) {
                laiwang.setText(data.getStringExtra("values"));
            }
            if (result.equals("mailplace")) {
                mailplace.setText(data.getStringExtra("values"));
            }

        } else if (requestCode == CAMERA && resultCode == RESULT_OK) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(this, "SD卡出现问题", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri uri = Uri.fromFile(new File(photoPath, "temp.jpg"));
            cropImage(uri, 360, 360, CROP_PICTURE);

        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            //获取所选图片的Uri
            Uri uri = data.getData();
            cropImage(uri, 360, 360, CROP_PICTURE);
        } else if (requestCode == CROP_PICTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            File file = new File(photoPath);
            for (File pic : file.listFiles()) {
                if (pic.getName().equals(fileName)) {
                    pic.delete();
                }
            }
            FileOutputStream fout = null;
            String filePath = photoPath + "/temp.jpg";
            try {
                fout = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            /**
             * 上传头像
             */
//            if (getUpLoadTask().getStatus() != AsyncTask.Status.RUNNING) {
//                getUpLoadTask().execute(ValuesH.URL + "person/changeIcon?accountid=" + getSharedPreferences("userInfo", MODE_PRIVATE).getString("accountid", ""), filePath, "picture");
//                proDialog = MyProDialog.createMyProDialog(this).setText("正在上传头像…");
//                proDialog.show();
//            }
            photo.setImageBitmap(bitmap);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
}