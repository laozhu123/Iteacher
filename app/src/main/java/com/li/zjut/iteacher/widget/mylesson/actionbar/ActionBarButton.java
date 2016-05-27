package com.li.zjut.iteacher.widget.mylesson.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.li.zjut.iteacher.R;


public class ActionBarButton extends View implements ActionBarElement
{
	private Drawable btnImg = null;
	private int width, height;
	int alignType = 0;

	public ActionBarButton(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ActionBarButton);
		btnImg = ta.getDrawable(R.styleable.ActionBarButton_img);
		ta.recycle();
		ta = context.obtainStyledAttributes(attrs, R.styleable.ActionBarElement);
		alignType = ta.getInt(R.styleable.ActionBarElement_align, 0);
		ta.recycle();
		init();
	}

	public ActionBarButton(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ActionBarButton(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		if (btnImg == null)
			btnImg = new ColorDrawable(0xffff7777);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
        height = MeasureSpec.getSize(heightMeasureSpec);  
        
        if(btnImg.getClass() == ColorDrawable.class)
		{
			width = height;
		}
		else
		{
			width = btnImg.getIntrinsicWidth() * height / btnImg.getIntrinsicHeight();
		}
		setMeasuredDimension(width + getPaddingStart() + getPaddingEnd(), height);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		int pad = getPaddingStart();
		btnImg.setBounds(pad, 0, width + pad, height);
		btnImg.draw(canvas);
	}

	@Override
	public AlignType getAlign()
	{
		return AlignType.values()[alignType];
	}

}
