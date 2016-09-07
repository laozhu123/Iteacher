package com.li.zjut.iteacher.activity.imteacher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView head;
    private TextView phone, name;
    private TextView alarm;
    private TextView sendMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.detail_info), true);
        initView();
        initData();
    }

    private void initView() {
        sendMassage = (TextView) findViewById(R.id.send_message);
        sendMassage.setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.send_short_message).setOnClickListener(this);
        alarm = (TextView) findViewById(R.id.alarm);

        head = (CircleImageView) findViewById(R.id.head);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        String sName = getIntent().getStringExtra("name");
        if (sName == null)
            name.setText("hehe");
        else
            name.setText(sName);
        String num = getIntent().getStringExtra("phone");
        if (num == null)
            phone.setText("1888656325");
        else
            phone.setText(num);
    }

    private void initData() {
        alarm.setVisibility(View.VISIBLE);
        alarm.setText(name.getText().toString() + "还未开通Iteacher,暂时无法使用发消息功能");
        sendMassage.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message:
                if (v.isSelected())
                    Toast.makeText(CallActivity.this, "hehe", Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent());
                break;
            case R.id.call:
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intentCall);
                break;
            case R.id.send_short_message:
                Intent intentSendShort = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone.getText().toString()));
                startActivity(intentSendShort);
                break;
            default:
                break;
        }
    }
}
