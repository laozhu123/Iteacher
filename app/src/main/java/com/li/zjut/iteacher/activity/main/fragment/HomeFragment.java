package com.li.zjut.iteacher.activity.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.wel_login.LoginActivity;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class HomeFragment extends Fragment {

    String Tag = "HomeFragment";

    private ImageView mImg_left;
    private CircleImageView mImg_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);

        initView(view);
        return view;
    }

    private void initView(View v) {
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.myday));


        mImg_profile = (CircleImageView) v.findViewById(R.id.profile_image);
        mImg_profile.setImageResource(R.mipmap.palette);
        mImg_profile.setVisibility(View.VISIBLE);
        mImg_profile.setOnClickListener(listener);

        ImageView rigImg = (ImageView) v.findViewById(R.id.right_img);
        rigImg.setVisibility(View.VISIBLE);
        rigImg.setImageResource(R.drawable.delete);
        rigImg.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.profile_image:
                    Log.d(Tag,"profile_image");
                    break;
                case R.id.right_img:
                    logout();
                    break;
            }
        }
    };


    /*
    * 测试退出用
    * */
    private void logout() {

        getActivity().getSharedPreferences(StaticData.USER_DATA, StaticData.SHARE_MODE).edit().putBoolean(SharePerfrence.UNLOGIN,true).apply();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
