package com.li.zjut.iteacher.widget.mylesson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.li.zjut.iteacher.R;


public class FromToPicker extends LinearLayout
		implements OnValueChangeListener, OnScrollListener
{
	private NumberPicker npFrom, npTo, lastChg;
	private TextView tvS, tvM, tvE;
	private final int minVal, maxVal;
	private final String txtS, txtM, txtE;

	public FromToPicker(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.FromToPicker);
		minVal = ta.getInt(R.styleable.FromToPicker_minValue, 0);
		maxVal = ta.getInt(R.styleable.FromToPicker_maxValue, 0);
		txtS = ta.getString(R.styleable.FromToPicker_txtStart);
		txtM = ta.getString(R.styleable.FromToPicker_txtMid);
		txtE = ta.getString(R.styleable.FromToPicker_txtEnd);
		ta.recycle();
		init(context);
	}

	public FromToPicker(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public FromToPicker(Context context)
	{
		this(context, null);
	}

	private void init(Context context)
	{
		setGravity(Gravity.CENTER_VERTICAL);
		npFrom = new NumberPickerEx(context);
		npTo = new NumberPickerEx(context);
		npFrom.setMinValue(minVal);
		npFrom.setMaxValue(maxVal);
		npFrom.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		npFrom.setOnValueChangedListener(this);
		npFrom.setOnScrollListener(this);
		npTo.setMinValue(minVal);
		npTo.setMaxValue(maxVal);
		npTo.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		npTo.setOnValueChangedListener(this);
		npTo.setOnScrollListener(this);
		tvS = new TextView(context);
		tvS.setText(txtS);
		tvM = new TextView(context);
		tvM.setText(txtM);
		tvE = new TextView(context);
		tvE.setText(txtE);

		LayoutParams lpnp = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		LayoutParams lptv = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(tvS, -1, lptv);
		addView(npFrom, -1, lpnp);
		addView(tvM, -1, lptv);
		addView(npTo, -1, lpnp);
		addView(tvE, -1, lptv);
	}

	public int getFromVal()
	{
		return npFrom.getValue();
	}

	public int getToVal()
	{
		return npTo.getValue();
	}
	
	public int setFromVal(int val)
	{
		if(val < minVal)
			val = minVal;
		else if(val > maxVal)
			val = maxVal;
		npFrom.setValue(val);
		return val;
	}

	public int setToVal(int val)
	{
		if(val < minVal)
			val = minVal;
		else if(val > maxVal)
			val = maxVal;
		npTo.setValue(val);
		return val;
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal)
	{
		lastChg = picker;
	}

	@Override
	public void onScrollStateChange(NumberPicker view, int scrollState)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			int valF = getFromVal(), valT = getToVal();
			if(lastChg != null && valF > valT)
			{
				if (lastChg == npFrom)
					npTo.setValue(valF);
				else if (lastChg == npTo)
					npFrom.setValue(valT);
			}
		}
	}

}
