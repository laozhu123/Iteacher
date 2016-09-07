package com.li.zjut.iteacher.activity.addresslist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class ApartmentActivity extends BaseActivity implements View.OnClickListener {

    private TextView txLeft, txRig;
    private View lineLeft, lineRig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.apartment), true);
        initView();
    }

    private void initView() {
        txLeft = (TextView) findViewById(R.id.tx_left);
        txRig = (TextView) findViewById(R.id.tx_rig);
        lineLeft = findViewById(R.id.line_left);
        lineRig = findViewById(R.id.line_rig);
        txLeft.setText(getString(R.string.school_apartment));
        txRig.setText(getString(R.string.college_apartment));
        txRig.setOnClickListener(this);
        txLeft.setOnClickListener(this);
        txLeft.setSelected(true);
        lineLeft.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_left:
                leftOrRight(true);
                break;
            case R.id.tx_rig:
                leftOrRight(false);
                break;
            default:
                break;
        }
    }

    /*设置选择项的背景颜色*/
    private void leftOrRight(boolean isLeft) {
        if (isLeft && !txLeft.isSelected()) {
            txLeft.setSelected(true);
            lineLeft.setSelected(true);
            txRig.setSelected(false);
            lineRig.setSelected(false);
            return;
        }
        if (!isLeft && !txRig.isSelected()){
            txLeft.setSelected(false);
            lineLeft.setSelected(false);
            txRig.setSelected(true);
            lineRig.setSelected(true);
        }
    }
}
