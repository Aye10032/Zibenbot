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

    public static TimeCycle PER_YEAR = new PerYear();
    public static TimeCycle PER_MONTH = new PerMonth();
    public static TimeCycle PER_WEEK = new PerWeek();
    public static TimeCycle PER_DAY = new PerDay();
    public static TimeCycle PER_HOUR = new PerHour();
    public static TimeCycle PER_HALF_HOUR = new PerHalfHour();
    public static TimeCycle PER_MIN = new PerMin();
    public static TimeCycle PER_SEC = new PerSec();

    public static Date getNextTimeFromNow(Date date, TimeCycle cycle){
        Date now = new Date();
        Date ret = (Date) date.clone();
        while (now.compareTo(ret) >= 0) {
            ret.setTime(cycle.getNextTime(ret).getTime());
        }
        return ret;
    }

  private static class PerYear implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + YEAR);
            return ret;
        }

    }

    private static class PerMonth implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Calendar ret = Calendar.getInstance();
            ret.setTime(date);
            ret.add(Calendar.MONTH, 1);
            return ret.getTime();
        }

    }

    private static class PerWeek implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + WEEK);
            return ret;
        }

    }

    private static class PerDay implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + DAY);
            return ret;
        }

    }

    private static class PerHalfHour implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + HOUR / 2);
            return ret;
        }

    }

    private static class PerHour implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + HOUR);
            return ret;
        }

    }

    private static class PerMin implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + MIN);
            return ret;
        }

    }

    private static class PerSec implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + SEC);
            return ret;
        }

    }


}
