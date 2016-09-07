package com.li.zjut.iteacher.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.activity.setting.SettingActivity;

/**
 * Created by LaoZhu on 2016/7/12.
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, null);

        MainActivity.content.setVisibility(View.VISIBLE);
        initView(view);
        return view;
    }

    private void initView(View view) {

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getString(R.string.my));
        view.findViewById(R.id.phone).setOnClickListener(this);
        view.findViewById(R.id.short_phone).setOnClickListener(this);
        view.findViewById(R.id.email).setOnClickListener(this);
        view.findViewById(R.id.qq).setOnClickListener(this);
        view.findViewById(R.id.setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone:
                break;
            case R.id.short_phone:
                break;
            case R.id.email:
                break;
            case R.id.qq:
                break;
            case R.id.setting:

                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
