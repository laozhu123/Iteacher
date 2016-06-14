package com.li.zjut.iteacher.activity.personinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;

public class EditPersonalInfoActivity extends BaseActivity{
	EditText editinfo;
	int result;
	
	String key_e;
	String key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_personal_info);
		key = getIntent().getStringExtra("edit_key");
		super.setContext(findViewById(R.id.toolbar),getString(R.string.change)+key,true);


		String values = getIntent().getStringExtra("edit_values");
		key_e = getIntent().getStringExtra("edit_key_e");


		editinfo = (EditText)findViewById(R.id.editinfo);
		editinfo.setText(values);
		TextView title_right = (TextView) findViewById(R.id.txTitleRig);
		title_right.setText(getString(R.string.store));
		title_right.setVisibility(View.VISIBLE);
		title_right.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.txTitleRig:
				getConnect();
				saveInfo();
				break;
			default:
				break;
			}
		}

		
	};
	private void getConnect(){
		int type = 0;
		if(key_e.equals("nickname")){
			type = 2;
		}
		if(key_e.equals("workphone")){
			type = 10;
		}
		if(key_e.equals("shortphone")){
			type = 11;
		}
		if(key_e.equals("weixin")){
			type = 12;
		}
		if(key_e.equals("skype")){
			type = 13;
		}
		if(key_e.equals("yixin")){
			type = 14;
		}
		if(key_e.equals("feixin")){
			type = 15;
		}
		if(key_e.equals("laiwang")){
			type = 16;
		}
		if(key_e.equals("mailplace")){
			type = 17;
		}

	}
	

		
	private void saveInfo() {
		SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		String values = editinfo.getText().toString();
		editor.putString(key_e, values);
		editor.apply();
		Intent intent = new Intent();
		intent.putExtra("key", key);
		intent.putExtra("key_e", key_e);
		intent.putExtra("values", values);
		
		setResult(RESULT_OK,intent);
		finish();
	}

}
