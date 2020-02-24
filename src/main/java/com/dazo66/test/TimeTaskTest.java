package com.dazo66.test;

import com.aye10032.Utils.TimeUtil.TaskCycle;
import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Utils.TimeUtil.TimeTaskPool;
import com.aye10032.Utils.TimeUtil.TimedTask;

import java.util.Date;

/**
 * @author Dazo66
 */
public class TimeTaskTest {

    public static void main(String[] args) {

        TimeTaskPool pool = new TimeTaskPool();
        pool.add(new TimedTask().setTiggerTime(new Date(System.currentTimeMillis() - 5000))
                .setTimes(5)
                .setCycle(TimeConstant.PER_MIN)
                .setRunnable(new print1()));

        TimedTask task1 = new TimedTask();
        task1.setTiggerTime(new Date(System.currentTimeMillis() + 1000))
                .setTimes(-1)
                .setCycle(new Pre5Min())
                .setRunnable(new print2());
        pool.add(task1);

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
