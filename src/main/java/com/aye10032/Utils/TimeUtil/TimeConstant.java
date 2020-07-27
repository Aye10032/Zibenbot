package com.aye10032.Utils.TimeUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Dazo66
 */
public class TimeConstant {

    public static int SEC = 1000;
    public static int MIN = 60 * SEC;
    public static int HOUR = 60 * MIN;
    public static int DAY = 24 * HOUR;
    public static int WEEK = 7 * DAY;
    public static int YEAR = 365 * DAY;

    public static ITimeAdapter NEXT_YEAR = new NextYear();
    public static ITimeAdapter NEXT_MONTH = new NextMonth();
    public static ITimeAdapter NEXT_WEEK = new NextWeek();
    public static ITimeAdapter NEXT_DAY = new NextDay();
    public static ITimeAdapter NEXT_HOUR = new NextHour();
    public static ITimeAdapter NEXT_HALF_HOUR = new NextHalfHour();
    public static ITimeAdapter NEXT_MIN = new NextMin();
    public static ITimeAdapter NEXT_SEC = new NextSec();

    /**
     * 当前时间的下一个
     * 不断运行cycel的循环直到找到现在的下一个
     * @param date 基准时间
     * @param adapter 时间适配器 得到下个时间
     * @return
     */
    public static Date getNextTimeFromNow(Date date, ITimeAdapter adapter){
        Date now = new Date();
        Date ret = (Date) date.clone();
        while (now.compareTo(ret) >= 0) {
            ret.setTime(adapter.getNextTime(ret).getTime());
        }
        return ret;
    }

  private static class NextYear implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + YEAR);
            return ret;
        }

    }

    private static class NextMonth implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Calendar ret = Calendar.getInstance();
            ret.setTime(date);
            ret.add(Calendar.MONTH, 1);
            return ret.getTime();
        }

    }

    private static class NextWeek implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + WEEK);
            return ret;
        }

    }

    private static class NextDay implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + DAY);
            return ret;
        }

    }

    private static class NextHalfHour implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + HOUR / 2);
            return ret;
        }

    }

    private static class NextHour implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + HOUR);
            return ret;
        }

    }

    private static class NextMin implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + MIN);
            return ret;
        }

    }

    private static class NextSec implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + SEC);
            return ret;
        }

    }


}
