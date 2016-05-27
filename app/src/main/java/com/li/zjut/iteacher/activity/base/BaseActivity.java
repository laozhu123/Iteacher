package com.li.zjut.iteacher.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;


public class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.left_img:
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
            left.setOnClickListener(listener);
        }

        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(title);
    }
}
