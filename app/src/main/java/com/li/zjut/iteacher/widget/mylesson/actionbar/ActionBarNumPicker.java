package com.li.zjut.iteacher.widget.mylesson.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.widget.mylesson.view.NumberPickerEx;


public class ActionBarNumPicker extends TextView
		implements ActionBarElement, OnClickListener, OnScrollListener
{
	private PopupWindow pop;
	private NumberPickerEx np;
	private OnValueChangeListener onValChange = null;

	int alignType = 0;
	private int minVal = 0, maxVal = 0;

	public ActionBarNumPicker(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ActionBarElement);
		alignType = ta.getInt(R.styleable.ActionBarElement_align, 0);
		ta.recycle();
		ta = context.obtainStyledAttributes(attrs,
				R.styleable.ActionBarNumPicker);
		minVal = ta.getInt(R.styleable.ActionBarNumPicker_minVal, 0);
		maxVal = ta.getInt(R.styleable.ActionBarNumPicker_maxVal, 0);
		ta.recycle();
		init(context);
	}

	public ActionBarNumPicker(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ActionBarNumPicker(Context context)
	{
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		np = new NumberPickerEx(context);
		np.setMaxValue(maxVal);
		np.setMinValue(minVal);
		pop = new PopupWindow(np, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new ColorDrawable(0xe0c0c0c0));

		np.setOnScrollListener(this);
		setOnClickListener(this);
	}

	@Override
	public AlignType getAlign()
	{
		return AlignType.values()[alignType];
	}

	@Override
	public void onClick(View v)
	{
		pop.setWidth(getWidth());
		pop.showAsDropDown(this);
	}

	public interface OnValueChangeListener
	{
		public void onValueChange(int val);
	}
	
	public void setOnValChange(OnValueChangeListener onValChange)
	{
		this.onValChange = onValChange;
	}
	
	@Override
	public void onScrollStateChange(NumberPicker view, int scrollState)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			int val = view.getValue();
			this.setText("��"+val+"��");
			invalidate();
			if(onValChange != null)
				onValChange.onValueChange(val);
		}
	}
}
