package com.dazo66.test;

import com.aye10032.TimeTask.DragraliaTask;
import com.aye10032.Utils.TimeUtil.TimeConstant;
import com.aye10032.Zibenbot;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class dragralia {

    public static void main(String[] args) throws IOException {

        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long last_data = calendar.getTimeInMillis() / 1000;
        long current = System.currentTimeMillis() / 1000;
        if ((current - 21600) / 86400 > (last_data - 21600) / 86400) {
            System.out.println("当前时间："+new Date(current*1000) + "上次时间："+new Date(last_data*1000) + "满足要求");
        }*/


        //System.out.println("<><1><><2>1<><><>123<><".replaceAll("<[^<>]*>+?", ""));
        System.out.println(System.currentTimeMillis());
        Zibenbot zibenbot = new Zibenbot();
        DragraliaTask task = new DragraliaTask(zibenbot);
        task.setTiggerTime(new Date(System.currentTimeMillis() + 10)).setCycle(TimeConstant.PER_MIN);
        zibenbot.pool.add(task);


    }


}
