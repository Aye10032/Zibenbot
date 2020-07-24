package com.dazo66.test;

import com.aye10032.Utils.TimeUtil.TaskCycle;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Dazo66
 */
public class TimeTaskTest {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();

        TaskCycle maiyaoCycle = date1 -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY, 19);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            Date now = calendar1.getTime();

            while (now.compareTo(date1) >= 0) {
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (0 <= hour && hour < 6) {
                    c.set(Calendar.HOUR_OF_DAY, 6);
                    date1.setTime(c.getTimeInMillis());
                } else if (6 <= hour && hour < 12) {
                    c.set(Calendar.HOUR_OF_DAY, 12);
                    date1.setTime(c.getTimeInMillis());
                } else if (12 <= hour && hour < 18) {
                    c.set(Calendar.HOUR_OF_DAY, 18);
                    date1.setTime(c.getTimeInMillis());
                } else {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    date1.setTime(c.getTimeInMillis() + 86400 * 1000);
                }
            }
            return date1;
        };

        System.out.println(maiyaoCycle.getNextTime(date));

    }



    public static class Pre5Min implements TaskCycle {

        public Date getNextTime(Date date) {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return getNextTime(new Date(date.getTime() + 60 * 1000 * 5));
            }
            return date;
        }

    }

    public static class print1 implements Runnable {
        public void run() {
            System.out.println("过去了1分钟");
        }
    }

    public static class print2 implements Runnable {
        public void run() {
            System.out.println("过去了5分钟");
        }
    }


}
