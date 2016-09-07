package com.li.zjut.iteacher.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.li.zjut.iteacher.R;


public class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.v_back:
                    finish();
                    break;
            }
        }
    };

    public void setContext(View view, String title, boolean show) {

        if (show) {
            ImageView left = (ImageView) view.findViewById(R.id.left_img);
            left.setImageResource(R.drawable.backarrow);
            left.setVisibility(View.VISIBLE);
            view.findViewById(R.id.v_back).setOnClickListener(listener);
        }

        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(title);
    }
}
