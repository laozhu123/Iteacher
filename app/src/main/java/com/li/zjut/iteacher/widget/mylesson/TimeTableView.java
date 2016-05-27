/**
 * @author XZiar
 */
package com.li.zjut.iteacher.widget.mylesson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


import com.li.zjut.iteacher.common.mylesson.SizeUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class TimeTableView extends View implements OnTouchListener
{
	protected Bitmap bufBM = null;
	protected Canvas bufCV = null;
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected TextPaint paintTxt = new TextPaint(Paint.ANTI_ALIAS_FLAG);

	private int viewWidth, viewHeight, width, height;
	private float blkPadX, blkPadY;
	private boolean isNeedReBuf = true;
	private LessonBlock[][] lessonMap = null;
	private List<LessonBlock> lessons = new ArrayList<LessonBlock>();
	private int curWeek = 1;

	private OnChooseListener chooseListener = null;

	/**
	 * constructor of TimeTableView
	 * 
	 * @param context
	 *            context
	 * @param blkSize
	 *            size of one block
	 */
	public TimeTableView(Context context, int blkSize)
	{
		super(context);
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		setOnTouchListener(this);
		setClickable(true);

		Log.v("tester", "TimeTable initialize");
		width = height = SizeUtil.dp2px(blkSize) + 1;
		viewHeight = height * 12;
		viewWidth = width * 7;
		blkPadX = width / 8f;
		blkPadY = height / 10f;

		paintLine.setColor(Color.LTGRAY);
		paintLine.setStrokeWidth(1.0f);
		paintLine.setStyle(Style.STROKE);
		paintTxt.setColor(Color.WHITE);
		paintTxt.setTextSize(width * 0.24f);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(viewWidth, viewHeight);
		Log.v("tester", "TTV onMeasure " + viewWidth + "," + viewHeight);
	}

	private void drawBlock(Canvas canvas, LessonBlock lb)
	{
		int lbTime = lb.getTime(), lbWD = lb.getWeekDay(),
				lbLast = lb.getLast();
		final int left = lbWD * width, top = lbTime * height,
				bottom = top + lbLast * height, halfH = height * lbLast / 2;
		for (; lbLast-- > 0;)
		{
			lessonMap[lbWD][lbTime++] = lb;
		}
		canvas.save();
		canvas.clipRect(left, top, left + width - 1, bottom - 1);
		canvas.drawColor(lb.getBlkColor());
		canvas.restore();

		StaticLayout sl = new StaticLayout(lb.getName(), paintTxt,
				(int) (width - blkPadX * 2), Alignment.ALIGN_NORMAL, 1.0f, 0.0f,
				true);
		canvas.save();
		canvas.translate(left + blkPadX, top + blkPadY);
		canvas.clipRect(0, 0, width, height);
		sl.draw(canvas);
		canvas.restore();

		sl = new StaticLayout(lb.getAppendix(), paintTxt,
				(int) (width - blkPadX * 2), Alignment.ALIGN_NORMAL, 1.0f, 0.0f,
				true);
		canvas.save();
		canvas.translate(left + blkPadX, top + halfH);
		canvas.clipRect(0, 0, width, halfH);
		sl.draw(canvas);
		canvas.restore();
	}

	protected void bufferDraw()
	{
		if (bufCV == null || bufBM == null || bufBM.getWidth() != viewWidth
				|| bufBM.getHeight() != viewHeight)
		{
			bufBM = Bitmap.createBitmap(viewWidth, viewHeight,
					Bitmap.Config.ARGB_8888);
			bufCV = new Canvas(bufBM);
		}

		Log.v("tester", "TTV bufDraw HW:" + bufCV.isHardwareAccelerated());
		bufCV.clipRect(0, 0, viewWidth, viewHeight);
		
		Drawable bg = getBackground();
		bg.setBounds(0, 0, viewWidth, viewHeight);
		bg.draw(bufCV);

		float baseY = 0;
		for (int a = 0; a < 12; a++)
		{
			baseY += height;
			bufCV.drawLine(0, baseY, viewWidth, baseY, paintLine);
		}
		lessonMap = new LessonBlock[7][12];
		for (LessonBlock lb : lessons)
		{
			if(lb.testWeek(curWeek))
				drawBlock(bufCV, lb);
		}

		isNeedReBuf = false;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Log.v("tester", "TTV draw HW:" + canvas.isHardwareAccelerated());
		if (isNeedReBuf)
			bufferDraw();
		canvas.drawBitmap(bufBM, 0, 0, null);

	}

	@Override
	public boolean onTouch(View v, MotionEvent e)
	{
		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_UP:
			// click
			int dx = (int) e.getX() - getLeft(), dy = (int) e.getY() - getTop();
			int w = dx / width, t = dy / height;
			String txt = "click week " + w + " time " + t;
			Log.v("tester", txt);
			if (chooseListener != null && lessonMap[w][t] != null)
				chooseListener.onChoose(lessonMap[w][t]);
			break;
		}
		return false;
	}

	interface OnChooseListener
	{
		public void onChoose(LessonBlock lb);
	}

	public void setChooseListener(OnChooseListener chooseListener)
	{
		this.chooseListener = chooseListener;
	}

	public void setLessons(List<LessonBlock> ls)
	{
		this.lessons = ls;
		isNeedReBuf = true;
	}
	
	public void setCurWeek(int curWeek)
	{
		this.curWeek = curWeek;
		isNeedReBuf = true;
	}

	@Override
	public void setBackground(Drawable background)
	{
		super.setBackground(background);
		isNeedReBuf = true;
	}
	
	
}
