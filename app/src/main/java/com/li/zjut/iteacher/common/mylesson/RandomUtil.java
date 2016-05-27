package com.li.zjut.iteacher.common.mylesson;

import java.util.Random;

public class RandomUtil
{
	private static Random random = new Random();

	public static int getColor()
	{
		int b = random.nextInt(0xff), g = random.nextInt(0xff), r = random.nextInt(0xff);
		System.out.println("ori,rgb=" + r + "," + g + "," + b);
		if (r + g + b > 0x240)
		{
			r = r * 4 / 5;
			g = g * 4 / 5;
			b = b * 4 / 5;
		}
		int c = (r << 16) + (g << 8) + b;
		System.out.println("fin:" + c + ",rgb=" + r + "," + g + "," + b);
		return c | 0xff000000;
	}
}
