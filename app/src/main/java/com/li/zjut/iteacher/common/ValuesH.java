package com.li.zjut.iteacher.common;

import java.io.File;

import android.os.Environment;

public class ValuesH {
	
	public static final int READING_TIMEOUT = 40000;
	//正式
	public static final String URL = "http://120.26.197.182:8080/zhrl/";
	
	//过儿服务器
//	public static final String URL= "http://115.28.247.219:8080/zhrl/";
	//测试
//	public static final String URL = "http://115.29.203.31:8080/zhrl/"; 
	public static final int PAGE_NUMBER = 10;
	
	public static String CURRENT_VERSION;
	public static String CURRENT_UPDATE_STATUS;
	public static final String ZTWBFilePath = new File(Environment.getExternalStorageDirectory(), "/ZTWB/").getPath();
	public static final String TempPath = ZTWBFilePath+"/temp.jpg";
	
	public static int WindowWidth;
}
