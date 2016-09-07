package com.li.zjut.iteacher.widget.common;

/**
 * Created by LaoZhu on 2016/6/27.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.li.zjut.iteacher.R;

public class HeadPhotoView extends ImageView{

    int circle_color;
    int circle_width;
    Bitmap photo;
    Context context;
    public static final int COLOR_NULL = 0;

    public HeadPhotoView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeadPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HeadPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init() {
        photo = BitmapFactory.decodeResource(getResources(), R.drawable.ex_img);
        circle_width = 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect src = new Rect(0,0,getWidth(),getWidth());
        Rect dst = new Rect(circle_width,circle_width,getWidth()-circle_width,getWidth()-circle_width);

        float round = getWidth()/2f;
        if(circle_color!=COLOR_NULL){
            Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlepaint.setColor(circle_color);
            canvas.drawCircle(round, round, round, circlepaint);
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(clipCircle(photo), src, dst, paint);

    }

    public void setCirCleColor(int color){
        circle_color = color;
        invalidate();
    }

    public void setCirCleWidth(int width){
        circle_width = width;
        invalidate();
    }
    public void setPhoto(int drawable){
        photo = BitmapFactory.decodeResource(getResources(), drawable);
        invalidate();
    }

    public void setPhoto(Uri uri){

        photo = BitmapFactory.decodeFile(uri.toString());
        if(photo==null){
            init();
        }
        invalidate();
    }

    public void setPhotoBitmap(Bitmap bitmap){
        photo = bitmap;
        invalidate();
    }

    public void setImageBitmap(Bitmap bitmap){
        photo = bitmap;
        invalidate();
    }

    public void setImageURI(Uri uri){
        photo = BitmapFactory.decodeFile(uri.toString());
        if(photo==null){
            init();
        }
        invalidate();
    }

    public void setImageDrawable(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        photo = bd.getBitmap();
        invalidate();
    }

    private Bitmap clipCircle(Bitmap bitmap){
        int width = getWidth()-circle_width*2;
        int height = getHeight()-circle_width*2;
        Rect src,dst;
        float round = width/2f;
        dst = new Rect(0, 0, width, height);
        RectF rectf = new RectF(dst);

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(rectf, round, round, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, dst, paint);


        return output;
    }

}