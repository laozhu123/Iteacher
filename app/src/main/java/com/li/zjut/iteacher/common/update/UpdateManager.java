package com.li.zjut.iteacher.common.update;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.bean.update.ReturnAppUpdate;
import com.li.zjut.iteacher.common.ValuesH;
import com.li.zjut.iteacher.widget.personinfo.MyDialog;
import com.li.zjut.iteacher.widget.personinfo.MyProDialog;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {

	public static final String Tag = "UpdateManager";
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 需要更新 */
	private static final int UPDATE = 3;
	/* 不需要更新 */
	private static final int NOUPDATE = 4;
	protected static final int NOUPDATE_ONSTARTAPP = 5;
	public static final int ONCLICK_CHECK_UPDATE = 6;
	public static final int ONSTARTAPP = 7;
	
	public static final int PAUSE = 8;

	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	InputStream inStream = null;
	
	ReturnAppUpdate appversion;
	MyDialog myDialog;
	MyProDialog myProDialog;
	
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			case UPDATE:
				showNoticeDialog();
				break;
			case NOUPDATE:
				Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();	
						
				break;
			case NOUPDATE_ONSTARTAPP:			
				finish();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate(int type) {
		/*
		 * if (isUpdate()) { // 显示提示对话框 showNoticeDialog(); } else {
		 * Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG)
		 * .show(); }
		 */
		isUpdate(type);
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	public void finish(){
		if(downloadListener!=null)
			downloadListener.onDownLoadFinish();
	}
	
	private void isUpdate(final int type) {
		// 获取当前软件版本
		final int versionCode = getVersionCode(mContext);
		String versionName = getVersionName(mContext);
		// 设置的版本号
		ValuesH.CURRENT_VERSION = "版本" + versionName;
		Log.e("Welcome","versionCode:"+versionCode);

		new Thread(new Runnable() {
			public void run() {
				String params = ValuesH.URL+"updateappversion/getnewversion?type=0";
				Log.e("Welcome",params);
				String ret = "fail";
				URL url;
				HttpURLConnection urlConnection = null;
				DataOutputStream out = null;
				BufferedReader in = null;
				try {
					long startTime=System.currentTimeMillis();
					url = new URL(params);
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
					urlConnection.setDoOutput(true);
					urlConnection.setUseCaches(false);    
					urlConnection.setConnectTimeout(ValuesH.READING_TIMEOUT);
					urlConnection.addRequestProperty("User-Agent", "Android Linux");
					urlConnection.setRequestMethod("POST");  
					out=new DataOutputStream(urlConnection.getOutputStream());					
					out.writeBytes(params);    
			        out.flush();    					
					
					if(urlConnection.getResponseCode()!=200) 
						ret = "fail";
					
					in = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream(), "UTF-8"),8196*4);
					
					ret = readStream(in);
					Log.e("Welcome",ret);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(out!=null)
						out.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					} // flush and close   
					try {
						if(in!=null)
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					urlConnection.disconnect();
				}
				

				if (!ret.equals("fail")) {
	
//					appversion = JSON.parseObject(ret, ReturnAppUpdate.class);
					int serviceCode = appversion.getVersioncode();
					Log.e("Welcome","serviceCode:"+serviceCode);
					if (serviceCode > versionCode) 
					{
						ValuesH.CURRENT_UPDATE_STATUS = "有新版本";
						mHandler.sendEmptyMessage(UPDATE);
					} else {
						if(type==ONSTARTAPP){
							mHandler.sendEmptyMessage(NOUPDATE_ONSTARTAPP);
						}
						if(type==ONCLICK_CHECK_UPDATE){
							mHandler.sendEmptyMessage(NOUPDATE);
						}
					}

				}
				else{
					mHandler.sendEmptyMessage(NOUPDATE_ONSTARTAPP);
				}
			}
				

			
		}).start();

	}
	

	/**
	 * @param mHashMap
	 * @return null or String
	 */
	public String getUpdateContent(HashMap<String, String> mHashMap) {

		if (mHashMap != null) {
			if (mHashMap.get("content") != null) {
				Log.i("1.17", mHashMap.get("content"));
				return mHashMap.get("content").replace("@", "\n");
			} else {
				return null;
			}

		}
		return null;

	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.example.zhongtong", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private String getVersionName(Context context) {
		String versionName = "";
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionName = context.getPackageManager().getPackageInfo(
					"com.example.zhongtong", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}


	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 构造对话框
		myDialog = MyDialog.createMyDialog(mContext).setText("发现新版本，马上更新吗？").setClickCancel(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				myDialog.dismiss();
			}
		}).setClickSure(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myDialog.dismiss();
				
				showDownloadDialog();
				
			}
		});
		myDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		myDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setTitle("正在下载……");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.jsoftupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
						finish();
					}
				});
		builder.setCancelable(false);
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		
//		myProDialog = MyProDialog.createMyProDialog(mContext).setText("正在下载……");
//		myProDialog.show();
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {

		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					Log.e("welocom", "dowLoadURL:"+appversion.getDownloadaddress());
					URL url = new URL(appversion.getDownloadaddress());

					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, appversion.getAppname()+".apk");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				} else {
					Log.e("jon", "error");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		
		File apkfile = new File(mSavePath, appversion.getAppname()+".apk");
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
	
	public String readStream(BufferedReader in) {
		StringBuffer ret = new StringBuffer(8196);
		String v;
		try {
			while ((v = in.readLine()) != null) {
				
				ret.append(v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ret.toString();
	}
	
	private OnDownLoadListener downloadListener;
	public interface OnDownLoadListener{
		public void onDownLoadFinish();
	}
	public void setOnDownLoadListener(OnDownLoadListener listener){
		downloadListener = listener;
	}
}