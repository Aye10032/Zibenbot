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

    private static class PerYear implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + YEAR);
            }
            return date;
        }

    }

    private static class PerMonth implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH, 1);
                date.setTime(cal.getTimeInMillis());
            }
            return date;
        }

    }

    private static class PerWeek implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + WEEK);
            }
            return date;
        }

    }

    private static class PerDay implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + DAY);
            }
            return date;
        }

    }

    private static class PerHalfHour implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + HOUR / 2);
            }
            return date;
        }

    }

    private static class PerHour implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + HOUR);
            }
            return date;
        }

    }

    private static class PerMin implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + MIN);
            }
            return date;
        }

    }

    private static class PerSec implements TimeCycle {

        @Override
        public Date getNextTime(Date date) {
            Date now = new Date();
            while (now.compareTo(date) >= 0) {
                date.setTime(date.getTime() + SEC);
            }
            return date;
        }

    }


}
