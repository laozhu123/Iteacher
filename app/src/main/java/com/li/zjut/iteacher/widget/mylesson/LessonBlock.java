package com.li.zjut.iteacher.widget.mylesson;

public interface LessonBlock
{
	public String getName();
	public String getAppendix();
	public int getBlkColor();
	public boolean testWeek(int week);
	public int getWeekDay();
	public int getTime();
	public int getLast();
}
