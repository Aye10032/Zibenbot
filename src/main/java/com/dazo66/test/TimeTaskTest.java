package com.dazo66.test;

import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Utils.TimeUtil.ITimeAdapter;

import java.util.Date;

/**
 * @author Dazo66
 */
public class TimeTaskTest {

    public static void main(String[] args) {
        long d = 1595847600000L;
        d = d + (TimeConstant.HOUR - TimeConstant.SEC - ((d - TimeConstant.SEC) % TimeConstant.HOUR)) + TimeConstant.SEC;

        System.out.println(new Date(d));
    }



    public static class Pre5Min implements ITimeAdapter {

        @Override
        public Date getNextTime(Date date) {
            Date ret = new Date();
            ret.setTime(date.getTime() + 5 * TimeConstant.MIN);
            return date;
        }

    }

    public static class print1 implements Runnable {
        @Override
        public void run() {
            System.out.println("过去了1分钟");
        }
    }

    public static class print2 implements Runnable {
        @Override
        public void run() {
            System.out.println("过去了5分钟");
        }
    }


}
