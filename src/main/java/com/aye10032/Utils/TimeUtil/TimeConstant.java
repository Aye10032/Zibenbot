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

    public static TaskCycle PER_YEAR = new PreYear();
    public static TaskCycle PER_MONTH = new PreMonth();
    public static TaskCycle PER_WEEK = new PreWeek();
    public static TaskCycle PER_DAY = new PreDay();
    public static TaskCycle PER_HOUR = new PreHour();
    public static TaskCycle PER_MIN = new PreMin();

    public static class PreYear implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) > 0) {
                return getNextTime(new Date(date.getTime() + YEAR));
            }
            return date;
        }

    }

    public static class PreMonth implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH, 1);
                return getNextTime(new Date(cal.getTime().getTime()));
            }
            return date;
        }

    }

    public static class PreWeek implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return getNextTime(new Date(date.getTime() + DAY * 7));
            }
            return date;
        }

    }

    public static class PreDay implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return getNextTime(new Date(date.getTime() + DAY));
            }
            return date;
        }

    }

    public static class PreHour implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return getNextTime(new Date(date.getTime() + HOUR));
            }
            return date;
        }

    }

    public static class PreMin implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return getNextTime(new Date(date.getTime() + MIN));
            }
            return date;
        }

    }


}
