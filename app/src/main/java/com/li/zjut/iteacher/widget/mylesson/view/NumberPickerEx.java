package com.li.zjut.iteacher.widget.mylesson.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class NumberPickerEx extends NumberPicker
{
	private int bakSDD, setH;
	private Field fdSDD;

	public NumberPickerEx(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public NumberPickerEx(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public NumberPickerEx(Context context)
	{
		this(context, null);
	}

	private void init()
	{
		try
		{
			fdSDD = NumberPicker.class
					.getDeclaredField("mSelectionDividersDistance");
			fdSDD.setAccessible(true);
			bakSDD = (int) fdSDD.get(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		setH = getMeasuredHeight();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int newH = getMeasuredHeight();
		if (newH != setH)// need to resize
		{
			float ratioH = newH * 1.0f / setH;
			float ratioW = (ratioH + 1) / 2;
			setMeasuredDimension((int) (getMeasuredWidth() * ratioW), newH);
			try
			{
				fdSDD.set(this, (int) (bakSDD * ratioH));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setDisplayedValues(String[] displayedValues)
	{
		super.setDisplayedValues(displayedValues);
		this.setMinValue(0);
		this.setMaxValue(displayedValues.length - 1);
	}

}
