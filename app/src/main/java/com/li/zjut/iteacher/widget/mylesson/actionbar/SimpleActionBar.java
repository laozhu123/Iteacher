package com.li.zjut.iteacher.widget.mylesson.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;

public class SimpleActionBar extends RelativeLayout
{
	private LinearLayout BarLeft, BarMid, BarRight;
	private TextView txtTitle;
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int pad = SizeUtil.dp2px(5);
	private boolean isSeparator = true;
	private String title = null;

	public SimpleActionBar(Context context)
	{
		super(context);
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, new int[] {
				android.R.attr.padding, R.attr.separator, R.attr.title });
		pad = ta.getDimensionPixelSize(0, 0);
		isSeparator = ta.getBoolean(1, true);
		title = ta.getString(2);
		ta.recycle();
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init(Context context)
	{
		BarLeft = new LinearLayout(context, null);
		LayoutParams parmL = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmL.addRule(RelativeLayout.ALIGN_PARENT_START);
		super.addView(BarLeft, -1, parmL);

		BarRight = new LinearLayout(context, null);
		LayoutParams parmR = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmR.addRule(RelativeLayout.ALIGN_PARENT_END);
		super.addView(BarRight, -1, parmR);

		BarMid = new LinearLayout(context, null);
		LayoutParams parmM = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmM.addRule(RelativeLayout.CENTER_IN_PARENT);
		super.addView(BarMid, -1, parmM);

		setTitle(title);

		paintLine.setColor(Color.GRAY);
		paintLine.setStrokeWidth(2.0f);
		paintLine.setStyle(Style.STROKE);
		Log.v("tester", "ActBar from ctx:" + context.getClass().getName());
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		Class<?>[] viewifs = child.getClass().getInterfaces();
		boolean isAdd = false;
		for (Class<?> viewif : viewifs)
		{
			if (viewif == ActionBarElement.class)
			{
				isAdd = true;// can add
				break;
			}
		}
		if (!isAdd)
			return;

		ActionBarElement abe = (ActionBarElement) child;
		child.setPaddingRelative(pad, 0, pad, 0);
		switch (abe.getAlign())
		{
		case center:
			BarMid.addView(child, -1, params);
			break;
		case left:
			BarLeft.addView(child, -1, params);
			break;
		case right:
			BarRight.addView(child, 0, params);
			break;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int height = heightMeasureSpec;
		if (isSeparator)
		{
			height = MeasureSpec.makeMeasureSpec(
					2 + MeasureSpec.getSize(heightMeasureSpec),
					MeasureSpec.getMode(heightMeasureSpec));
		}
		super.onMeasure(widthMeasureSpec, height);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		if (isSeparator)
		{
			int y = getBottom() - 1;
			Log.v("tester", "actBar draw,btm=" + getBottom() + "y=" + y);
			canvas.drawLine(0, y, canvas.getWidth(), y, paintLine);
		}
	}

	public void setTitle(String title)
	{
		this.title = title;
		if (title != null)
		{
			if (txtTitle == null)
			{
				txtTitle = new TextView(getContext(), null, android.R.style.Widget_TextView);
				BarMid.addView(txtTitle, 0, new ViewGroup.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			txtTitle.setText(title);
			postInvalidate();
		}
		else if (txtTitle != null)
		{
			BarMid.removeView(txtTitle);
			postInvalidate();
		}
	}

}
