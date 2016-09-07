package com.li.zjut.iteacher.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LaoZhu on 2016/7/14.
 */
public class DateUtils {
    public static int[] get7day(Calendar calendar) {
        int[] days = new int[7];
        int index = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        days[index] = calendar.get(Calendar.DAY_OF_MONTH);
        int dex = 0;
        for (int i = 0; i < index; i++) {
            calendar.roll(Calendar.DAY_OF_YEAR, -1);
            days[index - 1 - i] = calendar.get(Calendar.DAY_OF_MONTH);
            dex++;
        }
        calendar.roll(Calendar.DAY_OF_YEAR, dex);
        dex = 0;
        for (int i = 0; i < 6 - index; i++) {
            calendar.roll(Calendar.DAY_OF_YEAR, 1);
            days[index + i + 1] = calendar.get(Calendar.DAY_OF_MONTH);
            dex++;
        }
        calendar.roll(Calendar.DAY_OF_YEAR, -1 * dex);
        return days;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
