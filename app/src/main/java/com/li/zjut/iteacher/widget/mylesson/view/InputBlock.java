package com.li.zjut.iteacher.widget.mylesson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;


public class InputBlock extends LinearLayout
{
	Drawable icon;
	ImageView ico;
	private int pad = 0;
	public static int minH = SizeUtil.dp2px(40);

	public InputBlock(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				new int[] { android.R.attr.padding, R.attr.ico });
		pad = ta.getDimensionPixelSize(0, 0);
		icon = ta.getDrawable(1);
		ta.recycle();
		init(context);
	}

	public InputBlock(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public InputBlock(Context context)
	{
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		ico = new ImageView(context, null);
		setGravity(Gravity.CENTER_VERTICAL);
		setBackground(
				context.getResources().getDrawable(R.drawable.inputblock));
		if (pad < 2)
		{
			pad = 2;
			setPadding(pad, pad, pad, pad);
		}
		if (icon != null)
		{
			ico.setImageDrawable(icon);
			ico.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			int size = SizeUtil.dp2px(18);
			LayoutParams parm = new LayoutParams(size,
					size);
			parm.leftMargin = parm.rightMargin = SizeUtil.dp2px(10);
			super.addView(ico, 0, parm);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int setH = MeasureSpec.getSize(heightMeasureSpec);
		if (setH < minH)
			super.onMeasure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(minH, MeasureSpec.EXACTLY));
		else
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
