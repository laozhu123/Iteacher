package com.li.zjut.iteacher.widget.personinfo;


import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.li.zjut.iteacher.R;

public class PasswordDialog extends Dialog{
	Context context;
	TextView title, text;
	EditText password;
	TextView cancel, sure;
	public PasswordDialog(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	

	public PasswordDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init(context);
	}
	
	public static PasswordDialog createPasswordDialog(Context context){
		PasswordDialog dialog = new PasswordDialog(context, R.style.dialog_tran);
		return dialog;
		
	}
	private void init(Context context2) {
		setContentView(R.layout.password_dialog);
		title = (TextView)findViewById(R.id.title_big);
		text = (TextView)findViewById(R.id.title_small);
		password = (EditText)findViewById(R.id.password);
		cancel = (TextView)findViewById(R.id.cancel);
		sure = (TextView)findViewById(R.id.sure);
	}
	
	public PasswordDialog setClickCancel(android.view.View.OnClickListener listener){
		cancel.setOnClickListener(listener);
		return this;
	}
	public PasswordDialog setClickSure(android.view.View.OnClickListener listener){
		sure.setOnClickListener(listener);
		return this;
	}
	
	public String getEditText(){
		return password.getText().toString();
	}
	
	public PasswordDialog setTitle(String s){
		title.setText(s);
		return this;
	}
	
	public PasswordDialog setHint(String s){
		password.setHint(s);
		return this;
	}
	public PasswordDialog setText(String s){
		text.setText(s);
		return this;
	}
}
