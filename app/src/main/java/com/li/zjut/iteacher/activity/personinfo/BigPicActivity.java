package com.li.zjut.iteacher.activity.personinfo;

import java.io.File;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.widget.personinfo.MyProDialog;
import com.li.zjut.iteacher.widget.personinfo.Utils;
import com.li.zjut.iteacher.widget.personinfo.ZoomImageView;

public class BigPicActivity extends Activity{
	public static final String Tag = "BigpicActivity";
	ZoomImageView pic;
	View back;
	
	MyProDialog proDialog;
	
	
	public static void start(Context context, int state, String url){
		Intent intent = new Intent(context, BigPicActivity.class);
		intent.putExtra("state", state);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bigpic);
		proDialog = MyProDialog.createMyProDialog(this);
		pic = (ZoomImageView)findViewById(R.id.pic);
		back = findViewById(R.id.back);
		back.setOnClickListener(listener);
		pic.setImageBitmap(Utils.getBitmapFromDrawable(BigPicActivity.this, R.drawable.temp));
		if(getIntent().getIntExtra("state", 0)==0){
//			if(getImageTask().getStatus()!=Status.RUNNING){
//				proDialog.setText("正在加载图片……").show();
//				getImageTask().execute(getIntent().getStringExtra("url"));
//			}
		}else{
			Log.e(Tag, getIntent().getStringExtra("url"));
			pic.setImageBitmap(Utils.getBitmapFromUri(this, Uri.fromFile(new File(getIntent().getStringExtra("url")))));
		}
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pic:
//				finish();
				break;		
			case R.id.back:
//				finish();
				break;
				
			default:
				break;
			}
		}
	};
	
}
