package com.li.zjut.iteacher.widget.personinfo;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.li.zjut.iteacher.R;

public class MyDialog extends Dialog{
	Context context;
	
	TextView cancel, sure, text;
	public MyDialog(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init(context);
	}
	
	public static MyDialog createMyDialog(Context context){
		MyDialog dialog = new MyDialog(context, R.style.dialog_tran);
		return dialog;
		
	}
	private void init(Context context2) {
		setContentView(R.layout.my_dialog);
		text = (TextView)findViewById(R.id.text);
		cancel = (TextView)findViewById(R.id.cancel);
		sure = (TextView)findViewById(R.id.sure);
	}
	
	public MyDialog setText(String text){
		this.text.setText(text);
		return this;
	}
	
	public MyDialog setClickCancel(android.view.View.OnClickListener listener){
		cancel.setOnClickListener(listener);
		return this;
	}
	public MyDialog setClickSure(android.view.View.OnClickListener listener){
		sure.setOnClickListener(listener);
		return this;
	}
	
}

