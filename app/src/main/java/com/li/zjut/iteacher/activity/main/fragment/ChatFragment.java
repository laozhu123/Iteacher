package com.li.zjut.iteacher.activity.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class ChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        return view;
    }
}
