/**
 * @author XZiar
 */
package com.li.zjut.iteacher.widget.mylesson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.li.zjut.iteacher.common.mylesson.SizeUtil;


public class RowHeaders extends View
{
	private final static String[] times = { "8:00", "8:55", "9:55", "10:50",
			"11:45", "13:30", "14:25", "15:25", "16:20", "18:30", "19:25",
			"20:20" };

	protected Bitmap bufBM = null;
	protected Canvas bufCV = null;
	protected Paint paintTime = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintCnt = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);

	private boolean isNeedReBuf = true;
	private int viewWidth, viewHeight, width, height;// in px

	/**
	 * constructor of RowHeaders
	 * 
	 * @param context
	 *            context
	 * @param width
	 *            width
	 * @param rowHeight
	 *            height of one row
	 */
	public RowHeaders(Context context, int width, int rowHeight)
	{
		super(context);

		viewWidth = this.width = SizeUtil.dp2px(width);
		this.height = SizeUtil.dp2px(rowHeight) + 1;
		viewHeight = this.height * 12;

		paintTime.setTextAlign(Paint.Align.CENTER);
		paintTime.setTextSize(width * 0.45f);
		paintTime.setColor(Color.GRAY);
		paintCnt.setTextAlign(Paint.Align.CENTER);
		paintCnt.setTextSize(width * 0.65f);
		paintCnt.setTypeface(Typeface.DEFAULT_BOLD);
		paintCnt.setColor(Color.BLACK);
		paintLine.setColor(Color.LTGRAY);
		paintLine.setStrokeWidth(1.0f);
		paintLine.setStyle(Style.STROKE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		Log.v("tester", "Row onMeasure " + viewWidth + "," + viewHeight);
		setMeasuredDimension(viewWidth, viewHeight);
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
		
		Log.v("tester", "RowH bufDraw HW:" + bufCV.isHardwareAccelerated());
		bufCV.clipRect(0, 0, viewWidth, viewHeight);
		
		Drawable bg = getBackground();
		bg.setBounds(0, 0, viewWidth, viewHeight);
		bg.draw(bufCV);

		FontMetricsInt fontMetrics = paintTime.getFontMetricsInt();
		float baselineTime = (height * 1 / 2 - fontMetrics.top
				- fontMetrics.bottom) / 2f;
		fontMetrics = paintCnt.getFontMetricsInt();
		float baselineCnt = (height * 4 / 3 - fontMetrics.top
				- fontMetrics.bottom) / 2f;
		
		float baseX = width / 2f, baseY = 0;
		for (Integer a = 0; a < times.length;)
		{
			bufCV.drawText(times[a++], baseX, baseY + baselineTime, paintTime);
			bufCV.drawText(a.toString(), baseX, baseY + baselineCnt, paintCnt);
			baseY += height;
			bufCV.drawLine(0, baseY, width, baseY, paintLine);
		}
		isNeedReBuf = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		if(isNeedReBuf)
			bufferDraw();
		canvas.drawBitmap(bufBM, 0, 0, null);
		
	}
	
	@Override
	public void setBackground(Drawable background)
	{
		super.setBackground(background);
		isNeedReBuf = true;
	}
}
