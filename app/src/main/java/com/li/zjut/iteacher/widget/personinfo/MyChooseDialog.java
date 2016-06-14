package com.li.zjut.iteacher.widget.personinfo;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;

public class MyChooseDialog extends Dialog{
	
	RelativeLayout btn1,btn2;
	TextView btn_text1, btn_text2, title;
	ImageView btn_img1, btn_img2;
	
	Context context;
	public MyChooseDialog(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public MyChooseDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init();
	}
	
	public static MyChooseDialog createMyDialog(Context context){
		MyChooseDialog dialog = new MyChooseDialog(context, R.style.dialog_tran);
		return dialog;
		
	}
	
	private void init() {
		setContentView(R.layout.my_choose_dialog);
		btn1 = (RelativeLayout)findViewById(R.id.btn_1);
		btn2 = (RelativeLayout)findViewById(R.id.btn_2);
		btn_text1 = (TextView)findViewById(R.id.btn_text_1);
		btn_text2 = (TextView)findViewById(R.id.btn_text_2);
		btn_img1 = (ImageView)findViewById(R.id.btn_img_1);
		btn_img2 = (ImageView)findViewById(R.id.btn_img_2);
		title = (TextView)findViewById(R.id.dialog_title);
	}
	
	
	public MyChooseDialog setTextTitle(String text){
		title.setText(text);
		return this;
	}
	public MyChooseDialog setText1(String text){
		btn_text1.setText(text);
		return this;
	}
	public MyChooseDialog setText2(String text){
		btn_text2.setText(text);
		return this;
	}
	
	public MyChooseDialog setClick1(View.OnClickListener listener){
		btn1.setOnClickListener(listener);
		return this;
	}
	public MyChooseDialog setClick2(View.OnClickListener listener){
		btn2.setOnClickListener(listener);
		return this;
	}
	public MyChooseDialog setImage1(boolean b){
		if(b){
			btn_img1.setVisibility(View.VISIBLE);
		}else{
			btn_img1.setVisibility(View.GONE);
		}
		return this;
	}
	
	public MyChooseDialog setImage2(boolean b){
		if(b){
			btn_img2.setVisibility(View.VISIBLE);
		}else{
			btn_img2.setVisibility(View.GONE);
		}
		return this;
	}
	
	
	@Override
	public void show() {
		super.show();
	}
	
}
