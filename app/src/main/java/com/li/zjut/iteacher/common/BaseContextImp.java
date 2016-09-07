package com.li.zjut.iteacher.common;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;

public class BaseContextImp implements BaseContext{
	private View loading;
	private ImageView loading_img;
	AnimationDrawable animationDrawable;
	View content;
	Context context;
	
	public BaseContextImp(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	@Override
	public void addView(ViewGroup parent, View content) {
		this.content = content;
		loading = LayoutInflater.from(context).inflate(R.layout.loading_bg, null);
		parent.addView(loading);
		if(content.getParent()==null){
			parent.addView(content);
		}
		loading_img = (ImageView)loading.findViewById(R.id.loading_img);
		animationDrawable = (AnimationDrawable)loading_img.getDrawable();
	}

	@Override
	public void startLoading() {
		// TODO Auto-generated method stub
		if(content!=null){
			content.setVisibility(View.GONE);
			loading.setVisibility(View.VISIBLE);
			animationDrawable.start();
		}
	}

	@Override
	public void stopLoading() {
		// TODO Auto-generated method stub
		if(content!=null){
			content.setVisibility(View.VISIBLE);
			loading.setVisibility(View.GONE);
			animationDrawable.stop();
		}
		
	}
	
	
}
