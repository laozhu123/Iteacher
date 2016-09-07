package com.li.zjut.iteacher.common;

/**
 * Created by LaoZhu on 2016/6/27.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;

public class ImageLoader {
    private int required_size = 70;
    Context context;
    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    int stub_id= R.drawable.ex_img;
    PhotosQueue photosQueue=new PhotosQueue();
    PhotosLoaderThread photoLoaderThread=new PhotosLoaderThread();

    public ImageLoader(Context context){
        //Make the background thead low priority. This way it will not affect the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY-1);
        this.context = context;
        fileCache=new FileCache(context);
    }

    public void setStub_id(int drawable){
        stub_id = drawable;
    }
    public void setRequiredSize(int size){
        required_size = size;
    }

    public void DisplayImage(String url, Activity activity, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, activity, imageView);
            imageView.setImageDrawable(context.getResources().getDrawable(stub_id));
        }
    }


    private void queuePhoto(String url, Activity activity, ImageView imageView)
    {
        //This ImageView may be used for other images before. So there may be some old tasks in the queue. We need to discard them.
        photosQueue.Clean(imageView);
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        synchronized(photosQueue.photosLoadStack){
            photosQueue.photosLoadStack.push(p);
            photosQueue.photosLoadStack.notifyAll();
        }

        //start thread if it's not started yet
        if(photoLoaderThread.getState()==Thread.State.NEW)
            photoLoaderThread.start();
    }

    private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=required_size;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    //Task for the queue


    public void stopThread()
    {
        photoLoaderThread.interrupt();
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }


    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    //stores list of photos to download
    class PhotosQueue
    {
        private Stack<PhotoToLoad> photosLoadStack=new Stack<PhotoToLoad>();

        //removes all instances of this ImageView
        public void Clean(ImageView image)
        {
            for(int j=0 ;j<photosLoadStack.size();){
                if(photosLoadStack.get(j).imageView==image)
                    photosLoadStack.remove(j);
                else
                    ++j;
            }
        }
    }

    class PhotosLoaderThread extends Thread {
        public void run() {
            try {
                while(true)
                {
                    //thread waits until there are any images to load in the queue
                    if(photosQueue.photosLoadStack.size()==0)
                        synchronized(photosQueue.photosLoadStack){
                            photosQueue.photosLoadStack.wait();
                        }
                    if(photosQueue.photosLoadStack.size()!=0)
                    {
                        PhotoToLoad photoToLoad;
                        synchronized(photosQueue.photosLoadStack){
                            photoToLoad=photosQueue.photosLoadStack.pop();
                        }
                        Bitmap bmp=getBitmap(photoToLoad.url);
                        memoryCache.put(photoToLoad.url, bmp);
                        String tag=imageViews.get(photoToLoad.imageView);
                        if(tag!=null && tag.equals(photoToLoad.url)){
                            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a=(Activity)photoToLoad.imageView.getContext();
                            a.runOnUiThread(bd);
                        }
                    }
                    if(Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
                //allow thread to exit
            }
        }
    }



    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageView imageView;
        public BitmapDisplayer(Bitmap b, ImageView i){bitmap=b;imageView=i;}
        public void run()
        {
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
            else
                imageView.setImageDrawable(context.getResources().getDrawable(stub_id));
        }
    }



    public class MemoryCache {
        private HashMap<String, SoftReference<Bitmap>> cache=new HashMap<String, SoftReference<Bitmap>>();

        public Bitmap get(String id){
            if(!cache.containsKey(id))
                return null;
            SoftReference<Bitmap> ref=cache.get(id);
            return ref.get();
        }

        public void put(String id, Bitmap bitmap){
            cache.put(id, new SoftReference<Bitmap>(bitmap));
        }

        public void clear() {
            cache.clear();
        }
    }

    public class FileCache {

        private File cacheDir;

        public FileCache(Context context){
            //Find the dir to save cached images
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
            else
                cacheDir=context.getCacheDir();
            if(!cacheDir.exists())  //判断目录是否存在
                cacheDir.mkdirs();  //创建目录
        }

        public File getFile(String url){
            //I identify images by hashcode. Not a perfect solution, good for the demo.
            String filename=String.valueOf(url.hashCode());
            File f = new File(cacheDir, filename);
            return f;

        }

        public void clear(){
            File[] files=cacheDir.listFiles();
            for(File f:files)
                f.delete();
        }

    }
}