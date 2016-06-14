package com.li.zjut.iteacher.widget.personinfo;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.zjut.iteacher.R;

public class MyProDialog extends Dialog{
	Context context;
	
	ImageView load_img;
	TextView text;
	public MyProDialog(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	

	public MyProDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init(context);
	}
	
	public static MyProDialog createMyProDialog(Context context){
		MyProDialog dialog = new MyProDialog(context, R.style.dialog_tran);
		return dialog;
		
	}
	private void init(Context context2) {
		setContentView(R.layout.mypro_dialog);
		load_img = (ImageView)findViewById(R.id.loading_img);
		text = (TextView)findViewById(R.id.text);
		AnimationDrawable animationDrawable = (AnimationDrawable)load_img.getDrawable();
		animationDrawable.start();
	}
	public MyProDialog setText(String s){
		text.setText(s);
		return this;
	}
}
