package com.li.zjut.iteacher.widget.personinfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    


    
    public static boolean isSelf(Context context, String userid){
    	String accountid = getAcountid(context);
    	if(accountid.equals(userid)){
    		return true;
    	}
    	return false;
    }
    

    
    public static int Dp2Px(Context context, float dp) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dp * scale + 0.5f);  
    }  

    public static int Px2Dp(Context context, float px) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (px / scale + 0.5f);  
    }  
    


    /**
     * 检查GPS是否已打开
     * @param context
     * @return
     */
    public static boolean isOpenGPS(Context context){
    	LocationManager locationManager  
		    = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); 
			// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快） 
			boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); 
			// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位） 
			boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); 
			if (gps || network) { 
			return true; 
		} 

		return false; 
    }
    
    /**
     * 打开GPS
     * @param context
     */
    public static final void openGPS(Context context) { 
        Intent GPSIntent = new Intent(); 
        GPSIntent.setClassName("com.android.settings", 
                "com.android.settings.widget.SettingsAppWidgetProvider"); 
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE"); 
        GPSIntent.setData(Uri.parse("custom:3")); 
        try { 
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send(); 
        } catch (CanceledException e) { 
            e.printStackTrace(); 
        } 
    }
    
    /**
     * 打开手机wifi
     * @param context
     */
    public static final void openWifi(Context context){
    	WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    	if(!wifiManager.isWifiEnabled()){
    		wifiManager.setWifiEnabled(true);
    	}
    }

    /**
     * 将List<String> ids 转换成可以上传至服务器的json
     * @param ls
     * @return
     */
    public static String idsToString(List<String> ls) {
    	StringBuilder sb = new StringBuilder("[");
		for(int i=0;i<ls.size();i++){
			sb.append("{'userid':'"+ls.get(i)+"'}");
			if(i<ls.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
    	return sb.toString();
    }
    
    /**
     * 压缩bitmap
     * @param srcPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap bitmapCopress(String srcPath, int width, int height){
    	BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        int hh = height;//这里设置高度为800f  
        int ww = width;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = newOpts.outWidth / ww;  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = newOpts.outHeight / hh;  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩 
    }
    
    public static Bitmap compressImage(Bitmap image) {  
    	  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
  
    /**
     * 获取accountid
     * @param context
     * @return
     */
    public static final String getAcountid(Context context){
    	return context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("accountid", "");
    }
    
    /**
     * 获取comp_id
     * @param context
     * @return
     */
    public static final int getComp_id(Context context){
    	return context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getInt("companyid",0);
    }
    
    public static void removeInListByString(List<String> list, String string){
    	for(String s: list){
    		if(s.equals(string)){
    			list.remove(s);
    		}
    	}
    }
    
    /**
     * 获取文件的名字
     * @param path
     * @return
     */
    public static String getPathName(Context context, Uri uri){
    	String path = getRealFilePath(context, uri);
    	String[] s = path.split("/|\\.");
    	return s[s.length-2];
    }
    
   
    public static String getPathName(Context context, String name){
    	String[] s = name.split("/|\\.");
    	return s[s.length-2];
    }
    

    
    /**
     * 将Uri转换为系统路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    
    @SuppressLint("NewApi") 
    public static boolean isInView(View v, int x, int y){
   
		if(x>0&&x<v.getWidth()
				&&y<v.getHeight()&&y>0){
			return true;
		}else
			return false;
	}
    
    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getWidowWidth(Activity context){
    	DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
    }
    
    
    public static String URLEncode(String s){
    	if(!TextUtils.isEmpty(s)){
	    	try {
				return URLEncoder.encode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
    	}else{
    		return "";
    	}
    	
    }
    
    public static String getPicDescription(Context context, Map<String, String> map){
    	StringBuilder sb = new StringBuilder();
    	for(String key: map.keySet()){
    		sb.append("&"+key+"="+map.get(key));
    	}
    	return sb.toString();
    }
    
    public static Bitmap getBitmapFromUri(Context context, Uri uri){
    	try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static Bitmap getBitmapFromDrawable(Context context ,int drawable){
    	BitmapDrawable bd = (BitmapDrawable)context.getResources().getDrawable(drawable);
    	Bitmap bitmap = bd.getBitmap();
    	if(bitmap==null){
    		Log.e("Utils", "bitmap为空");
    	}
    	return bitmap;
    }
    

}